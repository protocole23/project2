package com.itwillbs.service;

import javax.inject.Inject;
import org.springframework.stereotype.Service;
import com.itwillbs.domain.WalletVO;
import com.itwillbs.persistence.WalletDAO;

@Service
public class WalletServiceImpl implements WalletService {

	@Inject
	private WalletDAO dao;

	@Override
	public WalletVO getWallet(int userId) throws Exception {
		return dao.getWallet(userId);
	}

	@Override
	public void updateBalance(WalletVO vo) throws Exception {
		dao.updateBalance(vo);
	}

	@Override
	public void createWallet(int userId) throws Exception {
		dao.createWallet(userId);
	}
}