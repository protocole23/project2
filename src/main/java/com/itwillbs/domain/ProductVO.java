package com.itwillbs.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ProductVO {
	private int productId;
	private int sellerId;
	private String title;
	private String description;
	private int price;
	private int status;
	private Date createdAt;
	private String location; 
    private Double lat;      
    private Double lon;

	// 파일 리스트 (첨부 이미지)
    private List<FileVO> images;
}