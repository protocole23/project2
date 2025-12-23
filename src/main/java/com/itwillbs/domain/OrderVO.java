package com.itwillbs.domain;

import lombok.Data;

@Data
public class OrderVO {
	private int orderId;
	private int productId;
	private int buyerId;
	private int status;
	
}