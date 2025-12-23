package com.itwillbs.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.itwillbs.domain.Criteria;
import com.itwillbs.domain.FileVO;
import com.itwillbs.domain.ProductVO;
import com.itwillbs.persistence.FileDAO;
import com.itwillbs.persistence.ProductDAO;

@Service
public class ProductServiceImpl implements ProductService {

	@Inject
	private ProductDAO productDAO;
	
	@Inject private FileDAO fileDAO;

	@Override
	public void insertProduct(ProductVO vo) throws Exception {
	    productDAO.insertProduct(vo);
	    System.out.println("productId after insert: " + vo.getProductId());

	    if(vo.getImages() != null) {
	        for(FileVO file : vo.getImages()) {
	            file.setRefType("product");
	            file.setRefId(vo.getProductId());
	            System.out.println("inserting file: " + file.getOriginName() + ", refId: " + file.getRefId());
	            fileDAO.insertFile(file);
	        }
	    }
	}


	@Override
	public ProductVO getProduct(int productId) throws Exception {
		ProductVO vo = productDAO.getProduct(productId);
	    vo.setImages(fileDAO.getFiles("product", productId));
	    return vo;
	}

	@Override
	public List<ProductVO> getProductList() throws Exception {
		return productDAO.getProductList();
	}
	
	@Override
	public List<ProductVO> getMyProductList(int sellerId) throws Exception {
		return productDAO.getMyProductList(sellerId);
	}

	@Override
	public void updateProduct(ProductVO vo) throws Exception {
		
		productDAO.updateProduct(vo);
	    

		if(vo.getImages() != null && !vo.getImages().isEmpty()) {
			for(FileVO file : vo.getImages()) {
				file.setRefType("product");
				file.setRefId(vo.getProductId());
				fileDAO.insertFile(file);
			}
		}
	}

	@Override
	public void updateStatus(int productId, int status) throws Exception {
		productDAO.updateStatus(productId, status);
	}
	
	@Override
	public List<ProductVO> getRecentProducts() throws Exception {
		return productDAO.getRecentProducts();
	}
	
	@Override
	public List<ProductVO> getListPaging(Criteria cri) throws Exception {
		
		List<ProductVO> list = productDAO.getListPaging(cri);
		
		for(ProductVO vo : list) {
	        List<FileVO> files = fileDAO.getFiles("product", vo.getProductId());
	        vo.setImages(files);  // images 필드에 파일 리스트 저장
	    }
		
		return list;
	}
	
	@Override
	public int getTotalCount(Criteria cri) throws Exception {
		return productDAO.getTotalCount(cri);
	}
}