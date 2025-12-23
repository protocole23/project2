package com.itwillbs.service;

import java.util.List;

import com.itwillbs.domain.Criteria;
import com.itwillbs.domain.ProductVO;

public interface ProductService {

	// 상품 등록
	void insertProduct(ProductVO vo) throws Exception;

	// 상품 조회
	ProductVO getProduct(int productId) throws Exception;

	// 상품 리스트
	List<ProductVO> getProductList() throws Exception;
	
	// 내 상품 리스트
		List<ProductVO> getMyProductList(int sellerId) throws Exception;

	// 상품 수정
	void updateProduct(ProductVO vo) throws Exception;

	// 상품 상태 변경 (삭제 포함)
	void updateStatus(int productId, int status) throws Exception;
	
	List<ProductVO> getRecentProducts() throws Exception;
	
	List<ProductVO> getListPaging(Criteria cri) throws Exception;
	
	int getTotalCount(Criteria cri) throws Exception;
	
	int getSellerIdByProductId(int productId) throws Exception;
}