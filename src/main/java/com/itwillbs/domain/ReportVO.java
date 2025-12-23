package com.itwillbs.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ReportVO {
	private int reportId;
	private int reporterId;
	private int targetId;
	private String reason;
	private LocalDateTime createdAt;
}
