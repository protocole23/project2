package com.itwillbs.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.itwillbs.domain.UserVO;
import com.itwillbs.persistence.UserDAO;

@Service
public class UserServiceImpl implements UserService {

	@Inject
	private UserDAO userDAO;

	@Override
	public UserVO login(String userid, String userpw) throws Exception {
		UserVO vo = userDAO.login(userid, userpw);
		if (vo == null) return null;
		if (vo.getStatus() == 1) return null;
		return vo;
	}

	@Override
	public boolean isUseridAvailable(String userid) throws Exception {
		return userDAO.checkUserid(userid) == 0;
	}

	@Override
	public void createUser(UserVO vo) throws Exception {
		userDAO.insertUser(vo);
	}

	@Override
	public UserVO getUser(String userid) throws Exception {
		return userDAO.getUser(userid);
	}

	@Override
	public void updateUser(UserVO vo) throws Exception {
		userDAO.updateUser(vo);
	}

	@Override
	public void deleteUser(String userid) throws Exception {
		userDAO.deleteUser(userid);
	}
}