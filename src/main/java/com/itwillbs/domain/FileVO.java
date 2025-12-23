package com.itwillbs.domain;

import lombok.Data;

@Data
public class FileVO {
	private int fileId;
	private String refType;
	private int refId;
	private String originName;
	private String storedName;
	private String filePath;
	private long fileSize;
	private String createdAt;

}
