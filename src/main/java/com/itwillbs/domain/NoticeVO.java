package com.itwillbs.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class NoticeVO {
	private int noticeId;
	private String title;
	private String content;
	private LocalDateTime createdAt;
}
