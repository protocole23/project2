package com.itwillbs.persistence;

import com.itwillbs.domain.UserVO;

public interface UserDAO {
	UserVO login(String userid, String userpw) throws Exception;
	void insertUser(UserVO vo) throws Exception;
	UserVO getUser(String userid) throws Exception;
	void updateUser(UserVO vo) throws Exception;
	void deleteUser(String userid) throws Exception;
	int checkUserid(String userid) throws Exception;
}