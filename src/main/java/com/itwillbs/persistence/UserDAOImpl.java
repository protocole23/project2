package com.itwillbs.persistence;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.itwillbs.domain.UserVO;

@Repository
public class UserDAOImpl implements UserDAO {

	private static final String namespace = "com.itwillbs.mapper.UserMapper";

	@Inject
	private SqlSession sqlSession;

	@Override
	public UserVO login(String userid, String userpw) throws Exception {
		Map<String, Object> paramMap = new HashMap<>();
	    paramMap.put("userid", userid);
	    paramMap.put("userpw", userpw);

	    return sqlSession.selectOne(namespace + ".login", paramMap);
	}

	@Override
	public void insertUser(UserVO vo) throws Exception {
		sqlSession.insert(namespace + ".insertUser", vo);
	}

	@Override
	public UserVO getUser(String userid) throws Exception {
		return sqlSession.selectOne(namespace + ".getUser", userid);
	}

	@Override
	public void updateUser(UserVO vo) throws Exception {
		sqlSession.update(namespace + ".updateUser", vo);
	}

	@Override
	public void deleteUser(String userid) throws Exception {
		sqlSession.update(namespace + ".deleteUser", userid);
	}

	@Override
	public int checkUserid(String userid) throws Exception {
		return sqlSession.selectOne(namespace + ".checkUserid", userid);
	}
}