package com.itwillbs.persistence;

import com.itwillbs.domain.EscrowVO;

public interface EscrowDAO {
	// 에스크로 생성
	void createEscrow(EscrowVO vo) throws Exception;
	// 조회
	EscrowVO getEscrow(int escrowId) throws Exception;
	// 상태 변경
	void updateStatus(EscrowVO vo) throws Exception;
}