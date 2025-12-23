package com.itwillbs.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class EscrowVO {
	private int escrowId;
	private int orderId;
	private int buyerId;
	private int sellerId;
	private int amount;
	private int status;
	private LocalDateTime createdAt;
}
