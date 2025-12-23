package com.itwillbs.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.itwillbs.domain.NoticeVO;
import com.itwillbs.persistence.NoticeDAO;

@Service
public class NoticeServiceImpl implements NoticeService {

	@Inject
	private NoticeDAO dao;

	@Override
	public List<NoticeVO> getNoticeList() throws Exception {
		return dao.getNoticeList();
	}

	@Override
	public NoticeVO getNotice(int noticeId) throws Exception {
		return dao.getNotice(noticeId);
	}

	@Override
	public void insertNotice(NoticeVO vo) throws Exception {
		dao.insertNotice(vo);
	}

	@Override
	public void updateNotice(NoticeVO vo) throws Exception {
		dao.updateNotice(vo);
	}

	@Override
	public void deleteNotice(int noticeId) throws Exception {
		dao.deleteNotice(noticeId);
	}
}