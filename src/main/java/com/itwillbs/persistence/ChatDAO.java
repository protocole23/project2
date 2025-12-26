package com.itwillbs.persistence;

import java.util.List;

import com.itwillbs.domain.ChatMessageVO;
import com.itwillbs.domain.ChatRoomVO;

public interface ChatDAO {
	// 채팅방 생성
	void createRoom(ChatRoomVO vo) throws Exception;
	
	// 채팅방 단건 조회
	ChatRoomVO getRoom(ChatRoomVO vo) throws Exception;

	// 유저가 참여 중인 채팅방 목록
	List<ChatRoomVO> getMyChatRooms(int userId) throws Exception;

	// 메시지 목록
	List<ChatMessageVO> getMessages(int roomId) throws Exception;
	
	// 메시지 전송
	void insertMessage(ChatMessageVO vo) throws Exception;
	
	// 마지막 메세지
	void updateLastMessage(ChatMessageVO vo) throws Exception;
	
	Integer findRoom(int productId, int buyerId, int sellerId) throws Exception;
	void createRoom(int productId, int buyerId, int sellerId) throws Exception;
}