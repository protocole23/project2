package com.itwillbs.persistence;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.itwillbs.domain.EscrowVO;

@Repository
public class EscrowDAOImpl implements EscrowDAO {

	private static final String NAMESPACE = "com.itwillbs.mapper.EscrowMapper.";

	@Inject
	private SqlSession sqlSession;

	@Override
	public void createEscrow(EscrowVO vo) throws Exception {
		sqlSession.insert(NAMESPACE + "createEscrow", vo);
	}

	@Override
	public EscrowVO getEscrow(int escrowId) throws Exception {
		return sqlSession.selectOne(NAMESPACE + "getEscrow", escrowId);
	}

	@Override
	public void updateStatus(EscrowVO vo) throws Exception {
		sqlSession.update(NAMESPACE + "updateStatus", vo);
	}
}
