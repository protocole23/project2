package com.itwillbs.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.itwillbs.domain.OrderVO;
import com.itwillbs.persistence.OrderDAO;

@Service
public class OrderServiceImpl implements OrderService {

	@Inject
	private OrderDAO dao;

	@Override
	public void createOrder(OrderVO vo) throws Exception {
		dao.insertOrder(vo);
	}

	@Override
	public OrderVO getOrder(int orderId) throws Exception {
		return dao.getOrder(orderId);
	}

	@Override
	public List<OrderVO> getOrderList() throws Exception {
		return dao.getOrderList();
	}

	@Override
	public void changeStatus(int orderId, int status) throws Exception {
		dao.updateStatus(orderId, status);
	}
}
