package com.itwillbs.domain;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ChatMessageVO {
	private int messageId;
	private int roomId;
	private int senderId;
	private String message;
	private LocalDateTime sendTime;
	private String senderNick;
}