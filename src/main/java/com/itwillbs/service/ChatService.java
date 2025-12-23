package com.itwillbs.service;

import java.util.List;
import com.itwillbs.domain.ChatRoomVO;
import com.itwillbs.domain.ChatMessageVO;

public interface ChatService {
	
		// 채팅방 생성
		void createRoom(ChatRoomVO vo) throws Exception;

		// 채팅방 단건 조회
		ChatRoomVO getRoom(ChatRoomVO vo) throws Exception;

		// 유저 채팅방 목록
		List<ChatRoomVO> getMyChatRooms(int userId) throws Exception;

		// 메시지 목록
		List<ChatMessageVO> getMessages(int roomId) throws Exception;

		// 텍스트 메시지 전송
		void insertMessage(ChatMessageVO vo) throws Exception;
		
		Integer findRoom(int productId, int buyerId) throws Exception;
		Integer createRoom(int productId, int buyerId) throws Exception;

}