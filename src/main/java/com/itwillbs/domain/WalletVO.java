package com.itwillbs.domain;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class WalletVO {
	private int walletId;
	private int userId;
	private int balance;
	private LocalDateTime updatedAt;
}