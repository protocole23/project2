package com.itwillbs.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.itwillbs.domain.EscrowVO;
import com.itwillbs.persistence.EscrowDAO;

@Service
public class EscrowServiceImpl implements EscrowService {

	@Inject
	private EscrowDAO dao;

	@Override
	public void createEscrow(EscrowVO vo) throws Exception {
		dao.createEscrow(vo);
	}

	@Override
	public EscrowVO getEscrow(int escrowId) throws Exception {
		return dao.getEscrow(escrowId);
	}

	@Override
	public void updateStatus(EscrowVO vo) throws Exception {
		dao.updateStatus(vo);
	}
}
