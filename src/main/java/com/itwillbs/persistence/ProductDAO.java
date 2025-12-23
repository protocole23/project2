package com.itwillbs.persistence;

import java.util.List;

import com.itwillbs.domain.Criteria;
import com.itwillbs.domain.ProductVO;

public interface ProductDAO {
	void insertProduct(ProductVO vo) throws Exception;
	ProductVO getProduct(int productId) throws Exception;
	List<ProductVO> getProductList() throws Exception;
	List<ProductVO> getMyProductList(int sellerId) throws Exception;
	void updateProduct(ProductVO vo) throws Exception;
	void updateStatus(int productId, int status) throws Exception;
	List<ProductVO> getRecentProducts() throws Exception;
	List<ProductVO> getListPaging(Criteria cri) throws Exception;
	int getTotalCount(Criteria cri) throws Exception;
}