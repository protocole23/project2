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
	    if (user == null) return "redirect:/user/login";

	    int buyerId = user.getId();

	    // DAO를 통해 sellerId 조회
	    int sellerId = productDAO.getSellerIdByProductId(productId);
	    
	    System.out.println("구매자ID(buyerId): " + buyerId);
	    System.out.println("판매자ID(sellerId): " + sellerId);
	    System.out.println("상품ID(productId): " + productId);

	    Integer roomId = service.findRoom(productId, buyerId);

	    if (roomId == null) {
	        roomId = service.createRoom(productId, buyerId);
	    }

	    return "redirect:/chat/room?roomId=" + roomId;
	}

	
}