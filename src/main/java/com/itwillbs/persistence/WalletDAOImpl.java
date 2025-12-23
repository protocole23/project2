package com.itwillbs.persistence;

import javax.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.itwillbs.domain.WalletVO;

@Repository
public class WalletDAOImpl implements WalletDAO {

	private static final String NAMESPACE = "com.itwillbs.mapper.WalletMapper.";

	@Inject
	private SqlSession sqlSession;

	@Override
	public WalletVO getWallet(int userId) throws Exception {
		return sqlSession.selectOne(NAMESPACE + "getWallet", userId);
	}

	@Override
	public void updateBalance(WalletVO vo) throws Exception {
		sqlSession.update(NAMESPACE + "updateBalance", vo);
	}

	@Override
	public void createWallet(int userId) throws Exception {
		sqlSession.insert(NAMESPACE + "createWallet", userId);
	}
}
