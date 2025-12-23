package com.itwillbs.service;

import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import com.itwillbs.domain.ChatRoomVO;
import com.itwillbs.domain.ChatMessageVO;
import com.itwillbs.persistence.ChatDAO;

@Service
public class ChatServiceImpl implements ChatService {

	@Inject
	private ChatDAO dao;

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

	@Override
	public void insertMessage(ChatMessageVO vo) throws Exception {
		dao.insertMessage(vo);
	}
	
	@Override
	public Integer findRoom(int productId, int buyerId) throws Exception {
		ChatRoomVO vo = new ChatRoomVO();
		vo.setProductId(productId);
		vo.setBuyerId(buyerId);
		ChatRoomVO room = dao.getRoom(vo);
		return room != null ? room.getRoomId() : null;
	}

	@Override
	public Integer createRoom(int productId, int buyerId) throws Exception {
		ChatRoomVO vo = new ChatRoomVO();
		vo.setProductId(productId);
		vo.setBuyerId(buyerId);
		dao.createRoom(vo);
		return vo.getRoomId();
	}
	
}
