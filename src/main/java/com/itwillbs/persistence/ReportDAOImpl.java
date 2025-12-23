package com.itwillbs.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.itwillbs.domain.ReportVO;

@Repository
public class ReportDAOImpl implements ReportDAO {

	private static final String NAMESPACE = "com.itwillbs.mapper.ReportMapper.";

	@Inject
	private SqlSession sqlSession;

	@Override
	public void createReport(ReportVO vo) throws Exception {
		sqlSession.insert(NAMESPACE + "createReport", vo);
	}

	@Override
	public List<ReportVO> getReportList() throws Exception {
		return sqlSession.selectList(NAMESPACE + "getReportList");
	}

	@Override
	public ReportVO getReport(int reportId) throws Exception {
		return sqlSession.selectOne(NAMESPACE + "getReport", reportId);
	}
}