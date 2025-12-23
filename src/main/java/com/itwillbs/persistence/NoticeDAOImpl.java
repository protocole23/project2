package com.itwillbs.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.itwillbs.domain.NoticeVO;

@Repository
public class NoticeDAOImpl implements NoticeDAO {

	private static final String NAMESPACE = "com.itwillbs.mapper.NoticeMapper";

	@Inject
	private SqlSession sqlSession;

	@Override
	public List<NoticeVO> getNoticeList() throws Exception {
		return sqlSession.selectList(NAMESPACE + ".getNoticeList");
	}

	@Override
	public NoticeVO getNotice(int noticeId) throws Exception {
		return sqlSession.selectOne(NAMESPACE + ".getNotice", noticeId);
	}

	@Override
	public void insertNotice(NoticeVO vo) throws Exception {
		sqlSession.insert(NAMESPACE + ".insertNotice", vo);
	}

	@Override
	public void updateNotice(NoticeVO vo) throws Exception {
		sqlSession.update(NAMESPACE + ".updateNotice", vo);
	}

	@Override
	public void deleteNotice(int noticeId) throws Exception {
		sqlSession.delete(NAMESPACE + ".deleteNotice", noticeId);
	}
}