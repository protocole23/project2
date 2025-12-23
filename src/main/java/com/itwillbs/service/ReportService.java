package com.itwillbs.service;

import java.util.List;

import com.itwillbs.domain.ReportVO;

public interface ReportService {
	void createReport(ReportVO vo) throws Exception;
	List<ReportVO> getReportList() throws Exception;
	ReportVO getReport(int reportId) throws Exception;
}
