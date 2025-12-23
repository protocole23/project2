package com.itwillbs.service;

import com.itwillbs.domain.EscrowVO;

public interface EscrowService {
	void createEscrow(EscrowVO vo) throws Exception;
	EscrowVO getEscrow(int escrowId) throws Exception;
	void updateStatus(EscrowVO vo) throws Exception;
}
