package com.itwillbs.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.itwillbs.domain.FileVO;

@Repository
public class FileDAOImpl implements FileDAO {

	private static final String namespace = "com.itwillbs.mapper.FileMapper";

	@Inject
	private SqlSession sqlSession;

	@Override
	public void insertFile(FileVO vo) throws Exception {
		sqlSession.insert(namespace + ".insertFile", vo);
	}

	@Override
	public List<FileVO> getFiles(String refType, int refId) throws Exception {
		FileVO param = new FileVO();
		param.setRefType(refType);
		param.setRefId(refId);
		return sqlSession.selectList(namespace + ".getFiles", param);
	}

	@Override
	public FileVO getFile(int fileId) throws Exception {
		return sqlSession.selectOne(namespace + ".getFile", fileId);
	}

	@Override
	public void deleteFile(int fileId) throws Exception {
		sqlSession.delete(namespace + ".deleteFile", fileId);
	}

	@Override
	public void deleteFiles(String refType, int refId) throws Exception {
		FileVO param = new FileVO();
		param.setRefType(refType);
		param.setRefId(refId);
		sqlSession.delete(namespace + ".deleteFiles", param);
	}
}
