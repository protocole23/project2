package com.itwillbs.service;

import java.util.List;

import com.itwillbs.domain.NoticeVO;

public interface NoticeService {
	List<NoticeVO> getNoticeList() throws Exception;
	NoticeVO getNotice(int noticeId) throws Exception;
	void insertNotice(NoticeVO vo) throws Exception;
	void updateNotice(NoticeVO vo) throws Exception;
	void deleteNotice(int noticeId) throws Exception;
}