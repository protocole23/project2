package com.itwillbs.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itwillbs.domain.ChatMessageVO;
import com.itwillbs.domain.ChatRoomVO;
import com.itwillbs.domain.UserVO;
import com.itwillbs.persistence.ChatDAO;
import com.itwillbs.persistence.ProductDAO;
import com.itwillbs.service.ChatService;

@Controller
@RequestMapping("/chat/*")
public class ChatController {

	@Inject
	private ChatService service;
	
	@Inject
	private ProductDAO productDAO;
	
	@Inject
	private ChatDAO chatDAO;

	@RequestMapping(value = "/rooms", method = RequestMethod.GET)
	public String rooms(HttpSession session, Model model) throws Exception {

		UserVO user = (UserVO) session.getAttribute("loginUser");
		int userId = user.getId();

		List<ChatRoomVO> rooms = service.getMyChatRooms(userId);
		model.addAttribute("rooms", rooms);

		return "/chat/rooms";
	}

	@RequestMapping(value = "/room", method = RequestMethod.GET)
	public String room(
	    @RequestParam(value = "roomId", required = false) Integer roomId,
	    HttpSession session,
	    Model model) throws Exception {

	    UserVO user = (UserVO) session.getAttribute("loginUser");
	    if (user == null) return "redirect:/user/login";

	    if (roomId == null) return "redirect:/chat/rooms"; // roomId 없으면 목록으로

	    List<ChatMessageVO> messages = service.getMessages(roomId);
	    model.addAttribute("messages", messages);
	    model.addAttribute("roomId", roomId);

	    return "/chat/room";
	}

	@RequestMapping(value = "/send", method = RequestMethod.POST)
	@ResponseBody
	public String send( @RequestParam int roomId,
						@RequestParam String message,
						HttpSession session) throws Exception {
		UserVO user = (UserVO) session.getAttribute("loginUser");
		if (user == null) return "redirect:/user/login";

		ChatMessageVO vo = new ChatMessageVO();
		vo.setRoomId(roomId);
		vo.setSenderId(user.getId());
		vo.setMessage(message);

		service.insertMessage(vo);

		return "OK";
	}
	
	@RequestMapping(value = "/start", method = RequestMethod.GET)
	public String start(@RequestParam int productId, HttpSession session) throws Exception {
		UserVO user = (UserVO) session.getAttribute("loginUser");
		if (user == null) {
			return "redirect:/user/login";
		}

		int buyerId = user.getId();

		// 1. 판매자 ID 조회
		int sellerId = productDAO.getSellerIdByProductId(productId);
		
		System.out.println("구매자ID(buyerId): " + buyerId);
		System.out.println("판매자ID(sellerId): " + sellerId);
		System.out.println("상품ID(productId): " + productId);

		// 2. 자신과의 채팅 방지 체크
		if (buyerId == sellerId) {
			return "redirect:/product/detail?productId=" + productId;
		}

		// 3. 기존에 생성된 채팅방이 있는지 확인 (중복 생성 방지)
		Integer roomId = service.findRoom(productId, buyerId, sellerId);

		// 4. 방이 없는 경우에만 새로 생성
		if (roomId == null) {
			ChatRoomVO vo = new ChatRoomVO();
			vo.setProductId(productId);
			vo.setBuyerId(buyerId);
			vo.setSellerId(sellerId);
			
			// 서비스 호출 (void 타입)
			service.createRoom(vo);
			
			// MyBatis의 useGeneratedKeys 덕분에 vo에 채워진 roomId를 꺼냄
			roomId = vo.getRoomId();
		}

		// 5. 최종 roomId로 이동
		return "redirect:/chat/room?roomId=" + roomId;
	}

	
}