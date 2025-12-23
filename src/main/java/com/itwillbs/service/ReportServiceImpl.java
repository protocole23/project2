package com.itwillbs.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.itwillbs.domain.ReportVO;
import com.itwillbs.persistence.ReportDAO;

@Service
public class ReportServiceImpl implements ReportService {

	@Inject
	private ReportDAO dao;

	@Override
	public void createReport(ReportVO vo) throws Exception {
		dao.createReport(vo);
	}

	@Override
	public List<ReportVO> getReportList() throws Exception {
		return dao.getReportList();
	}

	@Override
	public ReportVO getReport(int reportId) throws Exception {
		return dao.getReport(reportId);
	}
}
