package com.itwillbs.persistence;

import java.util.List;

import com.itwillbs.domain.FileVO;

public interface FileDAO {

	// 파일 메타데이터 저장
	public void insertFile(FileVO vo) throws Exception;

	// 특정 ref 조회 (예: product 10번의 파일 전체)
	public List<FileVO> getFiles(String refType, int refId) throws Exception;

	// 파일 1개 조회
	public FileVO getFile(int fileId) throws Exception;

	// 파일 삭제(DB)
	public void deleteFile(int fileId) throws Exception;

	// 파일 전체 삭제(ref 기준)
	public void deleteFiles(String refType, int refId) throws Exception;
}