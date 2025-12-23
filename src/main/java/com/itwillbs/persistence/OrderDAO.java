package com.itwillbs.persistence;

import java.util.List;
import com.itwillbs.domain.OrderVO;


public interface OrderDAO {

	void insertOrder(OrderVO vo) throws Exception;

	OrderVO getOrder(int orderId) throws Exception;

	List<OrderVO> getOrderList() throws Exception;

	void updateStatus(int orderId, int status) throws Exception;
}
