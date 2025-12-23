package com.itwillbs.persistence;

import java.util.List;

import com.itwillbs.domain.ReportVO;

public interface ReportDAO {
	void createReport(ReportVO vo) throws Exception;
	List<ReportVO> getReportList() throws Exception;
	ReportVO getReport(int reportId) throws Exception;
}
