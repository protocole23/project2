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
import com.itwillbs.service.ChatService;

@Controller
@RequestMapping("/chat/*")
public class ChatController {

	@Inject
	private ChatService service;

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
		@RequestParam("roomId") int roomId,
		Model model) throws Exception {

		List<ChatMessageVO> messages = service.getMessages(roomId);

		model.addAttribute("messages", messages);
		model.addAttribute("roomId", roomId);

		return "redirect:/chat/room?roomId=" + roomId;
	}

	@RequestMapping(value = "/send", method = RequestMethod.POST)
	@ResponseBody
	public String send(ChatMessageVO vo, HttpSession session) throws Exception {

		UserVO user = (UserVO) session.getAttribute("loginUser");
		vo.setSenderId(user.getId());

		service.insertMessage(vo);

		return "OK";
	}
	
	@RequestMapping(value = "/start", method = RequestMethod.GET)
	public String start(
		@RequestParam int productId,
		HttpSession session
	) throws Exception {

		UserVO user = (UserVO) session.getAttribute("loginUser");
		int buyerId = user.getId();

		Integer roomId = service.findRoom(productId, buyerId);

		if (roomId == null) {
			roomId = service.createRoom(productId, buyerId);
		}

		return "redirect:/chat/room?roomId=" + roomId;
	}

	
}