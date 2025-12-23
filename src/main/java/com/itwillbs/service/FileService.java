package com.itwillbs.service;

import java.util.List;
import com.itwillbs.domain.FileVO;

public interface FileService {
	public void insertFile(FileVO vo) throws Exception;
	public List<FileVO> getFiles(String refType, int refId) throws Exception;
	public FileVO getFile(int fileId) throws Exception;
	public void deleteFile(int fileId) throws Exception;
	public void deleteFiles(String refType, int refId) throws Exception;
}
