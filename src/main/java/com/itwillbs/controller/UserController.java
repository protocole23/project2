package com.itwillbs.controller;

import java.io.File;
import java.util.*;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itwillbs.domain.UserVO;
import com.itwillbs.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Inject
	private UserService userService;

	// 파일 업로드 처리
	private List<String> fileUploadProcess(MultipartHttpServletRequest multiRequest) throws Exception {
		List<String> fileList = new ArrayList<>();
		Iterator<String> fileNames = multiRequest.getFileNames();

		while (fileNames.hasNext()) {
			String fileParam = fileNames.next();
			MultipartFile file = multiRequest.getFile(fileParam);

			if (!file.isEmpty()) {
				String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
				String uploadPath = multiRequest.getSession().getServletContext().getRealPath("/resources/upload/");
				File dest = new File(uploadPath, fileName);
				file.transferTo(dest);

				fileList.add(fileName);
			}
		}
		return fileList;
	}

	// 회원가입 GET
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String joinGET() {
		return "user/join";
	}

	// 회원가입 POST
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String joinPOST(UserVO vo, RedirectAttributes rttr) throws Exception {
		logger.debug("회원가입 요청: {}", vo);

		// 프로필 이미지 업로드
//		List<String> files = fileUploadProcess(multi);
//		if (!files.isEmpty()) {
//			vo.setProfileImg(files.get(0));
//		}

		// USER 등록
		userService.createUser(vo);

		rttr.addFlashAttribute("msg", "joinSuccess");
		return "redirect:/user/login";
	}

	// 아이디 중복 체크
	@RequestMapping(value = "/idCheck", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> idCheck(@RequestParam("userid") String userid) throws Exception {
		boolean available = userService.isUseridAvailable(userid);
		Map<String, Object> map = new HashMap<>();
		map.put("available", available);
		map.put("message", available ? "사용 가능한 아이디입니다." : "이미 사용중인 아이디입니다.");
		return map;
	}

	// 로그인 GET
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginGET(@RequestParam(value = "oldPath", required = false) String oldPath, HttpSession session) {
		if (oldPath != null) {
			session.setAttribute("oldPath", oldPath);
		}
		return "user/login";
	}

	// 로그인 POST
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginPOST(@RequestParam("userid") String userid,
							@RequestParam("userpw") String userpw,
							HttpSession session,
							RedirectAttributes rttr,
							@RequestParam(value = "oldPath", required = false) String oldPath) throws Exception {
		
		logger.info("1. 로그인 시도 - ID: {}, PW: {}", userid, userpw);
		
		UserVO loginVO = userService.login(userid, userpw);
		
		// 로그 추가: DB에서 뭘 가져왔는지 확인
	    if (loginVO == null) {
	        logger.info("2. 결과: loginVO가 NULL입니다. (아이디/비번 불일치)");
	    } else {
	        logger.info("2. 결과: 회원정보 찾음 - Status 값: {}", loginVO.getStatus());
	    }
		
		if (loginVO != null && loginVO.getStatus() == 0) {
			logger.info("3. 로그인 성공! 메인으로 이동합니다.");
			
			session.setAttribute("loginUser", loginVO);
			session.setAttribute("userId", loginVO.getId());
			session.setAttribute("userid", loginVO.getUserid());
			session.setAttribute("userName", loginVO.getName());

			rttr.addFlashAttribute("msg", "loginSuccess");

			if (oldPath != null && !oldPath.isEmpty()) {
				return "redirect:" + oldPath;
			}
			return "redirect:/home";
		}
		
		logger.info("3. 로그인 실패! 다시 로그인 페이지로.");
		rttr.addFlashAttribute("msg", "loginFail");
		return "redirect:/user/login";
	}

	// 로그아웃
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutGET(HttpSession session) {
		session.invalidate();
		return "redirect:/home";
	}

	// 마이페이지 GET
	@RequestMapping(value = "/mypage", method = RequestMethod.GET)
	public String mypageGET(HttpSession session, Model model) throws Exception {
		String userid = (String) session.getAttribute("userid");
		UserVO vo = userService.getUser(userid);
		model.addAttribute("user", vo);
		return "user/mypage";
	}

	// 회원정보 수정 GET
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public String modifyGET(HttpSession session, Model model) throws Exception {
		String userid = (String) session.getAttribute("userid");
		UserVO vo = userService.getUser(userid);
		model.addAttribute("user", vo);
		return "user/modify";
	}

	// 회원정보 수정 POST
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modifyPOST(UserVO vo, MultipartHttpServletRequest multi, RedirectAttributes rttr) throws Exception {

		// 프로필 이미지 업로드 감지
		List<String> files = fileUploadProcess(multi);
		if (!files.isEmpty()) {
			vo.setProfileImg(files.get(0));
		}

		userService.updateUser(vo);

		rttr.addFlashAttribute("msg", "modifySuccess");
		return "redirect:/user/mypage";
	}

	// 회원탈퇴 GET
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String deleteGET(HttpSession session, Model model) throws Exception {
		String userid = (String) session.getAttribute("userid");
		UserVO vo = userService.getUser(userid);
		model.addAttribute("user", vo);
		return "user/delete";
	}

	// 회원탈퇴 POST
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deletePOST(@RequestParam("confirmText") String confirmText,
							 HttpSession session,
							 RedirectAttributes rttr) throws Exception {

		String userid = (String) session.getAttribute("userid");

		if (!"탈퇴하겠습니다.".equals(confirmText.trim())) {
			rttr.addFlashAttribute("msg", "deleteFail");
			return "redirect:/user/delete";
		}

		userService.deleteUser(userid);
		session.invalidate();

		rttr.addFlashAttribute("msg", "deleteSuccess");
		return "redirect:/";
	}
}
