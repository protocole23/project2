package com.itwillbs.service;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itwillbs.domain.ChatMessageVO;
import com.itwillbs.domain.ChatRoomVO;
import com.itwillbs.persistence.ChatDAO;
import com.itwillbs.persistence.ProductDAO;

@Service
public class ChatServiceImpl implements ChatService {

	@Inject
	private ChatDAO dao;
	
	@Inject
	private ProductDAO productDAO;
	
	private static final Logger logger = LoggerFactory.getLogger(ChatServiceImpl.class);

	@Override
	public void createRoom(ChatRoomVO vo) throws Exception {
		dao.createRoom(vo);
	}

	@Override
	public ChatRoomVO getRoom(ChatRoomVO vo) throws Exception {
		return dao.getRoom(vo);
	}

	@Override
	public List<ChatRoomVO> getMyChatRooms(int userId) throws Exception {
		return dao.getMyChatRooms(userId);
	}

	@Override
	public List<ChatMessageVO> getMessages(int roomId) throws Exception {
		return dao.getMessages(roomId);
	}
	
	@Transactional
	@Override
	public void insertMessage(ChatMessageVO vo) throws Exception {
		logger.info("=== insertMessage 호출 ===");
        logger.info("roomId: {}", vo.getRoomId());
        logger.info("senderId: {}", vo.getSenderId());
        logger.info("message: {}", vo.getMessage());

	    dao.insertMessage(vo);

	    logger.info("=== insertMessage 완료 ===");
		dao.updateLastMessage(vo);
	}
	
	@Override
	public Integer findRoom(int productId, int buyerId, int sellerId) throws Exception {
		ChatRoomVO vo = new ChatRoomVO();
		vo.setProductId(productId);
		vo.setBuyerId(buyerId);
		vo.setSellerId(sellerId);
		
		ChatRoomVO room = dao.getRoom(vo);
		return room != null ? room.getRoomId() : null;
	}
	
	@Transactional
	@Override
	public void createRoom(int productId, int buyerId, int sellerId) throws Exception {
		ChatRoomVO vo = new ChatRoomVO();
		vo.setProductId(productId);
		vo.setBuyerId(buyerId);
		vo.setSellerId(sellerId);
		
		dao.createRoom(vo);
	}
	
}
