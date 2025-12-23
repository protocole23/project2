package com.itwillbs.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.itwillbs.domain.Criteria;
import com.itwillbs.domain.ProductVO;

@Repository
public class ProductDAOImpl implements ProductDAO {

	private static final String NAMESPACE = "com.itwillbs.mapper.ProductMapper";

	@Inject
	private SqlSession sqlSession;
	
	@Inject
	private FileDAO fileDAO;

	@Override
	public void insertProduct(ProductVO vo) throws Exception {
		sqlSession.insert(NAMESPACE + ".insertProduct", vo);
	}

	@Override
	public ProductVO getProduct(int productId) throws Exception {
		return sqlSession.selectOne(NAMESPACE + ".getProduct", productId);
	}
	
	@Override
	public List<ProductVO> getMyProductList(int sellerId) throws Exception {
		return sqlSession.selectList(NAMESPACE + ".getMyProductList", sellerId);
	}

	@Override
	public List<ProductVO> getProductList() throws Exception {
		return sqlSession.selectList(NAMESPACE + ".getProductList");
	}

	@Override
	public void updateProduct(ProductVO vo) throws Exception {
		sqlSession.update(NAMESPACE + ".updateProduct", vo);
	}

	@Override
	public void updateStatus(int productId, int status) throws Exception {
	    Map<String, Object> param = new HashMap<>();
	    param.put("productId", productId);
	    param.put("status", status);

	    sqlSession.update(NAMESPACE + ".updateStatus", param);
	}
	
	@Override
	public List<ProductVO> getRecentProducts() throws Exception {
	    List<ProductVO> list = sqlSession.selectList(NAMESPACE + ".getRecentProducts");
	    for (ProductVO vo : list) {
	        vo.setImages(fileDAO.getFiles("product", vo.getProductId()));
	    }
	    return list;
	}
	
	@Override
	public List<ProductVO> getListPaging(Criteria cri) throws Exception {
		return sqlSession.selectList(NAMESPACE + ".getListPaging", cri);
	}
	
	@Override
	public int getTotalCount(Criteria cri) throws Exception {
		return sqlSession.selectOne(NAMESPACE + ".getTotalCount", cri);
	}
	
	@Override
    public int getSellerIdByProductId(int productId) throws Exception {
        return sqlSession.selectOne(NAMESPACE + ".getSellerIdByProductId", productId);
    }
}