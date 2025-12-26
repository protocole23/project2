package com.itwillbs.persistence;

import java.util.List;
import javax.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.itwillbs.domain.ChatRoomVO;
import com.itwillbs.domain.ChatMessageVO;

@Repository
public class ChatDAOImpl implements ChatDAO {

	private static final String namespace = "com.itwillbs.mapper.ChatMapper";

	@Inject
	private SqlSession sqlSession;

	@Override
	public void createRoom(ChatRoomVO vo) throws Exception {
		sqlSession.insert(namespace + ".createRoom", vo);
		
	}

	@Override
	public ChatRoomVO getRoom(ChatRoomVO vo) throws Exception {
		
		return sqlSession.selectOne(namespace + ".getRoom", vo);
		
	}

	@Override
	public List<ChatRoomVO> getMyChatRooms(int userId) throws Exception {
		
		return sqlSession.selectList(namespace + ".getMyChatRooms", userId);
	}

	@Override
	public List<ChatMessageVO> getMessages(int roomId) throws Exception {

		return sqlSession.selectList(namespace + ".getMessages", roomId);
	}

	@Override
	public void insertMessage(ChatMessageVO vo) throws Exception {
		sqlSession.insert(namespace + ".insertMessage", vo);
		sqlSession.update(namespace + ".updateLastMessage", vo);
		
	}

	@Override
	public void updateLastMessage(ChatMessageVO vo) throws Exception {
		sqlSession.update(namespace + ".updateLastMessage", vo);
		
	}

	@Override
	public Integer findRoom(int productId, int buyerId, int sellerId) throws Exception {
		ChatRoomVO param = new ChatRoomVO();
		param.setProductId(productId);
		param.setBuyerId(buyerId);
		param.setSellerId(sellerId);
		
		ChatRoomVO room = sqlSession.selectOne(namespace + ".getRoom", param);
		
		if (room != null) {
			return room.getRoomId();
		}
		return null;
	}

	@Override
	public void createRoom(int productId, int buyerId, int sellerId) throws Exception {
		ChatRoomVO param = new ChatRoomVO();
		param.setProductId(productId);
		param.setBuyerId(buyerId);
		param.setSellerId(sellerId);
		
		
		sqlSession.insert(namespace + ".createRoom", param);
		
	}

	

	
}