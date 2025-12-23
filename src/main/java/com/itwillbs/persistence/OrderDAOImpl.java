package com.itwillbs.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.itwillbs.domain.OrderVO;

@Repository
public class OrderDAOImpl implements OrderDAO {

	private static final String NAMESPACE = "com.itwillbs.mapper.OrderMapper";

	@Inject
	private SqlSession sqlSession;

	@Override
	public void insertOrder(OrderVO vo) throws Exception {
		sqlSession.insert(NAMESPACE + ".insertOrder", vo);
	}

	@Override
	public OrderVO getOrder(int orderId) throws Exception {
		return sqlSession.selectOne(NAMESPACE + ".getOrder", orderId);
	}

	@Override
	public List<OrderVO> getOrderList() throws Exception {
		return sqlSession.selectList(NAMESPACE + ".getOrderList");
	}

	@Override
	public void updateStatus(int orderId, int status) throws Exception {
		OrderVO vo = new OrderVO();
		vo.setOrderId(orderId);
		vo.setStatus(status);
		sqlSession.update(NAMESPACE + ".updateStatus", vo);
	}
}
