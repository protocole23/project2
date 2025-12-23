package com.itwillbs.domain;

import lombok.Data;

@Data
public class ChatRoomVO {
	private int roomId;
	private int buyerId;
	private int sellerId;
	private int productId;
}