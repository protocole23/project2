<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<jsp:include page="/WEB-INF/views/include/Header.jsp" />

<style>
    /* 상품 리스트 전용 스타일 */
    .section-title { margin: 40px 0 20px; font-size: 22px; font-weight: bold; color: #333; }
    
    .product-grid {
        display: grid;
        grid-template-columns: repeat(3, 1fr); /* 3열 배치 */
        gap: 25px;
        margin-bottom: 50px;
    }

    .product-card {
        background: #fff;
        border-radius: 12px;
        overflow: hidden;
        box-shadow: 0 4px 10px rgba(0,0,0,0.05);
        transition: transform 0.2s, box-shadow 0.2s;
        cursor: pointer;
        border: 1px solid #eee;
    }

    .product-card:hover {
        transform: translateY(-5px);
        box-shadow: 0 8px 20px rgba(0,0,0,0.1);
    }

    .product-img {
        width: 100%;
        height: 200px;
        background-color: #f0f0f0;
        object-fit: cover;
    }

    .product-info { padding: 15px; }
    .product-name { font-size: 16px; font-weight: bold; margin-bottom: 8px; color: #333; }
    .product-price { font-size: 18px; color: #ff8a3d; font-weight: 800; }
    .product-date { font-size: 12px; color: #999; margin-top: 10px; }

    /* 반응형: 화면이 작아지면 2열로 변경 */
    @media (max-width: 768px) {
        .product-grid { grid-template-columns: repeat(2, 1fr); }
    }
</style>

<div class="main-container">
    <h2 class="section-title">🔥 최근 등록된 상품</h2>
    
    <div class="product-grid">
	    <c:forEach var="product" items="${latestProducts}">
	        <div class="product-card" onclick="location.href='${pageContext.request.contextPath}/product/detail?productId=${product.productId}'">

	            
	            <c:choose>
	                <%-- 1. 상품에 이미지 리스트가 비어있지 않은지 확인 --%>
	                <c:when test="${not empty product.images}">
	                    <%-- 2. 리스트의 첫 번째 이미지(index 0)를 가져와서 storedName 사용 --%>
	                    <img src="/product/image/${product.images[0].storedName}" 
	                         class="product-img" 
	                         alt="${product.title}">
	                </c:when>
	                
	                <%-- 3. 이미지가 없는 경우 기본 이미지 출력 --%>
	                <c:otherwise>
	                    <div class="product-img" style="display:flex; align-items:center; justify-content:center; background:#eee; height:200px; color:#999;">
	                        이미지 없음
	                    </div>
	                </c:otherwise>
	            </c:choose>
	            
	            <div class="product-info">
	                <div class="product-name">${product.title}</div>
	                <div class="product-price">
	                    <fmt:formatNumber value="${product.price}" pattern="#,###"/>원
	                </div>
	            </div>
	        </div>
	    </c:forEach>
	</div>

    <div style="text-align: center; margin-top: 30px;">
        <form action="/product/productlist" method="GET" style="display:inline;">
            <button type="submit" class="action-button" style="padding: 15px 30px; font-size: 16px;">상품 전체보기 ▶</button>
        </form>
    </div>
</div>

<jsp:include page="/WEB-INF/views/include/Footer.jsp" />