package com.itwillbs.persistence;

import java.util.List;

import com.itwillbs.domain.NoticeVO;

public interface NoticeDAO {
	List<NoticeVO> getNoticeList() throws Exception;
	NoticeVO getNotice(int noticeId) throws Exception;
	void insertNotice(NoticeVO vo) throws Exception;
	void updateNotice(NoticeVO vo) throws Exception;
	void deleteNotice(int noticeId) throws Exception;
}