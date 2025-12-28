package com.itwillbs.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.itwillbs.component.RecFileComponent;
import com.itwillbs.domain.Criteria;
import com.itwillbs.domain.FileVO;
import com.itwillbs.domain.PageVO;
import com.itwillbs.domain.ProductVO;
import com.itwillbs.persistence.FileDAO;
import com.itwillbs.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {

	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	private static final String UPLOAD_PATH = "/usr/local/tomcat/upload/";

	@Inject
	private ProductService productService;
	
	@Inject
	private FileDAO fileDAO;
	
	@Inject
	private RecFileComponent recFileComponent;

	// 상품 등록 페이지
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public String registerGET() throws Exception {
		return "/product/register";
	}

	// 상품 등록하기
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String registerPOST(ProductVO vo,
            @RequestParam(value="uploadFiles", required=false) MultipartFile[] images,
            HttpSession session) throws Exception {
		
		System.out.println("POST lat: " + vo.getLat() + ", lon: " + vo.getLon());
		
		System.out.println("=== FILE UPLOAD DEBUG START ===");
		System.out.println("images is null? " + (images == null));
		System.out.println("images length: " + (images == null ? "null" : images.length));
		if(images != null) {
			for(int i = 0; i < images.length; i++) {
				System.out.println("images[" + i + "] name = " + images[i].getOriginalFilename());
				System.out.println("images[" + i + "] size = " + images[i].getSize());
			}
		}
		System.out.println("=== FILE UPLOAD DEBUG END ===");
		
		com.itwillbs.domain.UserVO user = (com.itwillbs.domain.UserVO) session.getAttribute("loginUser");

	    if (user == null) {
	        return "redirect:/user/login";
	    }
	    
	    Integer sellerId = user.getId();

		 // 1. 파일 먼저 처리
	    vo.setSellerId(sellerId);

	    List<FileVO> fileList = new ArrayList<>();

	    if(images != null) {
	        for(MultipartFile file : images) {
	            if(file.isEmpty()) continue;

	            // === RecFileComponent 적용 ===
	            String storedName = recFileComponent.thumbUpload(file); 
	            if(storedName != null) {
	                FileVO fvo = new FileVO();
	                fvo.setOriginName(file.getOriginalFilename());
	                fvo.setStoredName(storedName);
	                fvo.setFilePath("/upload/"); // RecFileComponent 경로
	                fvo.setFileSize(file.getSize());
	                fileList.add(fvo);
	            }
	        }
	    }

		//  핵심
		vo.setImages(fileList);

		productService.insertProduct(vo);

        return "redirect:/product/productlist";
	}

	// 상품 수정 페이지
	@RequestMapping(value="/update", method=RequestMethod.GET)
	public String editGET(@RequestParam("productId") int productId,
						  HttpSession session, Model model) throws Exception {

		ProductVO vo = productService.getProduct(productId);
		com.itwillbs.domain.UserVO user = (com.itwillbs.domain.UserVO) session.getAttribute("loginUser");

		if (user == null || vo.getSellerId() != user.getId()) {
	        return "redirect:/product/productlist";
	    }
		
		
		List<FileVO> fileList = fileDAO.getFiles("product", productId); 
	    vo.setImages(fileList);

		model.addAttribute("product", vo);
		return "/product/update";
	}

	// 상품 수정 처리
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String editPOST(ProductVO vo, 
            @RequestParam(value="uploadFiles", required=false) MultipartFile[] images,
            HttpSession session) throws Exception {

		com.itwillbs.domain.UserVO user = (com.itwillbs.domain.UserVO) session.getAttribute("loginUser");

		if (user == null) {
	        return "redirect:/user/login";
	    }
		
		int realSellerId = productService.getSellerIdByProductId(vo.getProductId());
	    
	    if (realSellerId != user.getId()) {
	        logger.warn("수정 권한 없음: 유저 {}, 상품번호 {}", user.getId(), vo.getProductId());
	        return "redirect:/product/productlist";
	    }

		// 파일 업로드 처리
		List<FileVO> fileList = new ArrayList<>();

		if(images != null) {
			for(MultipartFile file : images) {
				if(file.isEmpty()) continue;

				String storedName = recFileComponent.thumbUpload(file);

	            FileVO fvo = new FileVO();
	            fvo.setOriginName(file.getOriginalFilename());
	            fvo.setStoredName(storedName);
	            fvo.setFilePath("/upload/"); // recFileComponent 기준 경로
	            fvo.setFileSize(file.getSize());
	            
	            fileList.add(fvo);
			}
	    }
		vo.setImages(fileList);
	    productService.updateProduct(vo);
	    return "redirect:/product/detail?productId=" + vo.getProductId();
	}

	// 파일 업로드 처리 메소드
	private List<String> fileUploadProcess(MultipartHttpServletRequest multiRequest) throws Exception {

		List<String> fileList = new ArrayList<>();
		Iterator<String> fileNames = multiRequest.getFileNames();

		while(fileNames.hasNext()) {
			String fileParamName = fileNames.next();
			MultipartFile mFile = multiRequest.getFile(fileParamName);

			if(mFile == null || mFile.isEmpty()) continue;

			String originFileName = mFile.getOriginalFilename();
			String uuid = UUID.randomUUID().toString();
			String storedFileName = uuid + "_" + originFileName;

			fileList.add(storedFileName);

			File file = new File(UPLOAD_PATH + "\\" + storedFileName);
			if(!file.exists()) {
				if(file.getParentFile().mkdirs()) {
					file.createNewFile();
				}
			}

			mFile.transferTo(file);
			logger.debug("파일 업로드 성공: " + storedFileName);
		}

		return fileList;
	}
	
	// 상품 목록 조회
	@RequestMapping(value="/productlist", method=RequestMethod.GET)
	public String listGET(Model model, Criteria cri) throws Exception {
	    logger.debug("listGET() 호출");
	    
	    List<ProductVO> productList = productService.getListPaging(cri);
		model.addAttribute("productList", productList);

		PageVO pageVO = new PageVO();
		pageVO.setCri(cri);
		pageVO.setTotalCount(productService.getTotalCount(cri));

		model.addAttribute("pageVO", pageVO);	
	    
	    return "/product/productlist"; 
	}
	
	// 상품 상세 조회
	@RequestMapping(value="/detail", method=RequestMethod.GET)
	public String detailGET(@RequestParam("productId") int productId, Model model) throws Exception {
	    logger.debug("detailGET() 호출!");
	    
	    ProductVO vo = productService.getProduct(productId);
	    
	    model.addAttribute("product", vo);
	    
	    return "/product/detail";
	}
	
	// 상품 삭제 처리
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String deleteGET(@RequestParam("productId") int productId, 
		HttpSession session) throws Exception {
		
		Integer userId = (Integer) session.getAttribute("userId");
		
		if (userId == null) {
			return "redirect:/user/login";
		}

		// 삭제할 상품의 정보 가져오기
		ProductVO vo = productService.getProduct(productId);

		// 상품이 없거나 sellerId가 본인(userId)과 다르면 리스트로 보냄
		if (vo == null || vo.getSellerId() != userId) {
			logger.warn("인가되지 않은 사용자(ID: {})가 상품 ID: {} 삭제 시도", userId, productId);
			return "redirect:/product/productlist";
		}

		// soft delete : 상태 1 = 삭제 처리
		productService.updateStatus(productId, 1);

		return "redirect:/product/productlist";
	}
	
	// 이미지 반환 매핑
	@RequestMapping(value="/image/{fileName:.+}", method=RequestMethod.GET)
	public void serveImage(@PathVariable("fileName") String fileName, HttpServletResponse response) throws IOException {
	    File file = new File("/usr/local/tomcat/upload/", fileName); // 실제 저장 경로
	    if(file.exists()) {
	        // MIME 타입 설정 (이미지 확장자에 따라 자동 설정 가능)
	        String mimeType = Files.probeContentType(file.toPath());
	        if(mimeType == null) {
	            mimeType = "application/octet-stream";
	        }
	        response.setContentType(mimeType);
	        response.setContentLengthLong(file.length());

	        // 파일 스트림 전송
	        try (FileInputStream fis = new FileInputStream(file);
	             OutputStream os = response.getOutputStream()) {
	            byte[] buffer = new byte[4096];
	            int bytesRead;
	            while((bytesRead = fis.read(buffer)) != -1) {
	                os.write(buffer, 0, bytesRead);
	            }
	        }
	    } else {
	        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	    }
	}
}
