package com.itwillbs.persistence;

import com.itwillbs.domain.WalletVO;

public interface WalletDAO {
	WalletVO getWallet(int userId) throws Exception;
	void updateBalance(WalletVO vo) throws Exception;
	void createWallet(int userId) throws Exception;
}