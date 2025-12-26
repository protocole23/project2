<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/WEB-INF/views/include/Header.jsp" />
<link rel="stylesheet" href="https://cdn.lineicons.com/4.0/lineicons.css" />

<style>
body {
    font-family: 'Noto Sans KR', sans-serif;
    background-color: #f8f9fa;
    margin: 0;
    padding: 0;
}

/* 페이지 제목 */
.pageTitle {
    font-size: 28px;
    font-weight: 800;
    text-align: center;
    margin-bottom: 30px;
    color: #ff8a3d;
}

/* 검색 영역 */
#searchForm {
    display: flex;
    justify-content: center;
    margin-bottom: 25px;
}

#searchForm {
    display: flex;
    justify-content: center;
    margin-bottom: 25px;
    width: 100%;
}

.footSearch {
    width: 100%;
    max-width: 500px;
    display: flex;
    justify-content: center;
}

#searchForm {
    display: flex;
    justify-content: center;
    width: 100%;
    margin-bottom: 25px;
}

.footSearch {
    width: 100%;
    max-content: 500px; /* 최대 너비 제한 */
    display: flex;
    justify-content: center;
}

#subSearch {
    display: flex !important; /* 가로 정렬 강제 */
    flex-direction: row !important;
    align-items: stretch !important; /* 자식 요소 높이 통일 */
    width: 100%;
    max-width: 500px;
}

#subSearch input.form-control {
    flex: 1 !important;
    height: 45px !important; /* 명확한 높이 지정 */
    border: 1px solid #ccc !important;
    border-right: none !important; /* 버튼과 만나는 선 제거 */
    border-radius: 6px 0 0 6px !important;
    margin: 0 !important; /* 기본 여백 제거 */
    padding: 0 15px !important;
    box-sizing: border-box !important;
}

#subSearch button.btn {
    width: 55px !important;
    height: 45px !important; /* input과 동일한 높이 강제 */
    background-color: #ff8a3d !important;
    border: none !important;
    border-radius: 0 6px 6px 0 !important;
    margin: 0 !important;
    padding: 0 !important;
    display: flex !important;
    justify-content: center !important;
    align-items: center !important;
    cursor: pointer !important;
}

/* 카드형 상품 리스트 */
.productList {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    gap: 20px;
    padding: 0;
    list-style: none;
}

.productList li {
    width: 220px;
    background-color: #fff;
    border-radius: 12px;
    overflow: hidden;
    box-shadow: 0 4px 12px rgba(0,0,0,0.08);
    transition: transform 0.2s, box-shadow 0.2s;
}

.productList li:hover {
    transform: translateY(-5px);
    box-shadow: 0 8px 20px rgba(0,0,0,0.15);
}

.prodThumb {
    width: 100%;
    height: 150px;
    background-color: #f5f5f5;
    display: flex;
    align-items: center;
    justify-content: center;
    overflow: hidden;
    transition: background-color 0.3s;
}

.prodThumb img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: transform 0.3s;
}

.productList li:hover .prodThumb img {
    transform: scale(1.05);
}

.prodInfo {
    padding: 12px;
}

.prodTitle {
    font-size: 16px;
    font-weight: 700;
    color: #333;
    margin: 8px 0 5px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}

.prodPrice {
    font-size: 14px;
    font-weight: 600;
    color: #e74c3c;
    margin: 0 0 5px;
}

.prodSeller {
    font-size: 12px;
    color: #777;
    margin: 0;
}

/* 판매 상태 */
.prodStatus {
    display: inline-block;
    font-size: 12px;
    font-weight: 600;
    padding: 4px 8px;
    border-radius: 6px;
    margin-bottom: 5px;
    color: #fff;
}

.status1 { background-color: #28a745; } /* 판매중 */
.status2 { background-color: #ffc107; color: #333; } /* 예약중 */
.status3 { background-color: #6c757d; } /* 거래완료 */

/* 페이징 */
.pagination {
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 5px;
    margin-top: 30px;
    flex-wrap: wrap;
}

.pagination li {
    list-style: none;
}

.pagination li a {
    display: block;
    padding: 6px 12px;
    border-radius: 6px;
    border: 1px solid #ddd;
    background-color: #fff;
    color: #333;
    text-decoration: none;
    font-size: 14px;
    transition: 0.3s;
}

.pagination li.active a {
    background-color: #ff8a3d;
    color: #fff;
    border-color: #ff8a3d;
}

.pagination li a:hover {
    background-color: #e07934;
    color: #fff;
}

/* 상품 등록 버튼 */
section .btn {
    display: block;
    margin: 30px auto 0;
    padding: 10px 18px;
    background-color: #28a745;
    color: #fff;
    border-radius: 6px;
    border: none;
    font-size: 14px;
    font-weight: 600;
    cursor: pointer;
    transition: background-color 0.3s;
}

section .btn:hover {
    background-color: #218838;
}

/* 반응형 */
@media (max-width: 768px) {
    .productList li {
        width: 45%;
    }
}

@media (max-width: 480px) {
    /* 기존 flex-direction: column을 제거하여 가로 배치 유지 */
    #subSearch {
        flex-direction: row; 
    }
    
    #subSearch .btn {
        width: 55px; /* 모바일에서도 버튼 너비 유지 */
    }
}
</style>
<section class="section fix-layout">
	<h2 class="pageTitle">상품 목록</h2>

	<form action="/product/productlist" method="get" style="margin-bottom: 20px;" id="searchForm">
		<div class="footSearch">
			<div id="subSearch">
				<input type="text" placeholder="검색어 입력" value="${pageVO.cri.search}" name="search" class="form-control">
				<button type="submit" class="btn">
	                <i class="lni lni-search-alt lni-lg"></i>
	            </button>
			</div>
		</div>
	</form>

	<c:if test="${empty productList}">
		<p class="text-center">등록된 상품이 없습니다.</p>
	</c:if>

	<ul class="productList">
		<c:forEach var="item" items="${productList}">
			<li>
				<a href="/product/detail?productId=${item.productId}">
					<div class="prodThumb">
						<c:if test="${not empty item.images}">
		                    <c:forEach var="img" items="${item.images}">
		                        <img src="/product/image/${img.storedName}" alt="${item.title}" style="max-width:100px;">
		                    </c:forEach>
		                </c:if>
		                <c:if test="${empty item.images}">
		                    <p>이미지 없음</p>
		                </c:if>
					</div>
					<div class="prodInfo">
					
						<c:choose>
							<c:when test="${item.status eq 0}">
								<span class="prodStatus status1">판매중</span>
							</c:when>
							<c:when test="${item.status eq 1}">
								<span class="prodStatus status2">예약중</span>
							</c:when>
							<c:when test="${item.status eq 2}">
								<span class="prodStatus status3">거래완료</span>
							</c:when>
						</c:choose>
						
						<h3 class="prodTitle">${item.title}</h3>
						<p class="prodPrice"><fmt:formatNumber value="${item.price}" type="currency"/>원</p>
						<p class="prodSeller">판매자: ${item.sellerId}</p>
					</div>
				</a>
			</li>
		</c:forEach>
	</ul>

	<div class="pagination" style="text-align: center; margin-top: 20px;">
		<c:if test="${pageVO.prev}">
			<li><a href="?page=${pageVO.startPage - 1}&search=${pageVO.cri.search}"><i class="lni lni-arrow-left"></i></a></li>
		</c:if>

		<c:forEach var="i" begin="${pageVO.startPage}" end="${pageVO.endPage}">
			<li class="${pageVO.cri.page == i ? 'active' : ''}">
				<a href="?page=${i}&search=${pageVO.cri.search}">${i}</a>
			</li>
		</c:forEach>

		<c:if test="${pageVO.next}">
			<li><a href="?page=${pageVO.endPage + 1}&search=${pageVO.cri.search}"><i class="lni lni-arrow-right"></i></a></li>
		</c:if>
	</div>
	
	<div style="text-align: center; margin-top: 30px;">
	    <c:choose>
	        <c:when test="${not empty sessionScope.loginUser}">
	            <button class="btn" type="button" onclick="location.href='/product/register'">
	                <i class="lni lni-plus"></i> 상품 등록
	            </button>
	        </c:when>
	        <c:otherwise>
	            <button class="btn" type="button" style="background-color: #6c757d;" 
	                    onclick="if(confirm('상품 등록은 로그인이 필요합니다. 로그인 페이지로 이동하시겠습니까?')) { location.href='/user/login'; }">
	                <i class="lni lni-plus"></i> 상품 등록
	            </button>
	        </c:otherwise>
	    </c:choose>
	</div>
</section>

<jsp:include page="/WEB-INF/views/include/Footer.jsp" />
