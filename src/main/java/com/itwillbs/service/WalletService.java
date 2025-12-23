package com.itwillbs.service;

import com.itwillbs.domain.WalletVO;

public interface WalletService {
	WalletVO getWallet(int userId) throws Exception;
	void updateBalance(WalletVO vo) throws Exception;
	void createWallet(int userId) throws Exception;
}