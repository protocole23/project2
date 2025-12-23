package com.itwillbs.service;

import com.itwillbs.domain.UserVO;

public interface UserService {
	UserVO login(String userid, String userpw) throws Exception;
	boolean isUseridAvailable(String userid) throws Exception;
	void createUser(UserVO vo) throws Exception;
	UserVO getUser(String userid) throws Exception;
	void updateUser(UserVO vo) throws Exception;
	void deleteUser(String userid) throws Exception;
}