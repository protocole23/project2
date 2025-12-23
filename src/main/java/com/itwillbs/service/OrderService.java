package com.itwillbs.service;

import java.util.List;

import com.itwillbs.domain.OrderVO;

public interface OrderService {

	void createOrder(OrderVO vo) throws Exception;

	OrderVO getOrder(int orderId) throws Exception;

	List<OrderVO> getOrderList() throws Exception;

	void changeStatus(int orderId, int status) throws Exception;
}
