package com.itwillbs.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.itwillbs.persistence.FileDAO;
import com.itwillbs.domain.FileVO;

@Service
public class FileServiceImpl implements FileService {

	@Inject
	private FileDAO dao;

	@Override
	public void insertFile(FileVO vo) throws Exception {
		dao.insertFile(vo);
	}

	@Override
	public List<FileVO> getFiles(String refType, int refId) throws Exception {
		return dao.getFiles(refType, refId);
	}

	@Override
	public FileVO getFile(int fileId) throws Exception {
		return dao.getFile(fileId);
	}

	@Override
	public void deleteFile(int fileId) throws Exception {
		dao.deleteFile(fileId);
	}

	@Override
	public void deleteFiles(String refType, int refId) throws Exception {
		dao.deleteFiles(refType, refId);
	}
}
