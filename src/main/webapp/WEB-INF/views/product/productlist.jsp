<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/WEB-INF/views/include/Header.jsp" />

<style>
/* 카드형 상품 리스트 */
.productList {
	display: flex;
	flex-wrap: wrap;
	gap: 20px;
	padding: 0;
	list-style: none;
}

.productList li {
	width: 200px;
	border: 1px solid #ddd;
	border-radius: 8px;
	overflow: hidden;
	box-shadow: 0 2px 5px rgba(0,0,0,0.1);
	background-color: #fff;
	transition: transform 0.2s;
}

.productList li:hover {
	transform: translateY(-5px);
}

.prodThumb {
	width: 100%;
	height: 150px;
	overflow: hidden;
	text-align: center;
	background-color: #f5f5f5;
}

.prodThumb img {
	width: 100%;
	height: 100%;
	object-fit: cover;
}

.prodInfo {
	padding: 10px;
}

.prodTitle {
	font-size: 16px;
	font-weight: bold;
	margin: 0 0 5px 0;
}

.prodPrice {
	font-size: 14px;
	color: #e74c3c;
	margin: 0 0 5px 0;
}

.prodSeller {
	font-size: 12px;
	color: #888;
	margin: 0;
}
</style>
<section class="section fix-layout">
	<h2 class="pageTitle">상품 목록</h2>

	<form action="/product/productlist" method="get" style="margin-bottom: 20px;" id="searchForm">
		<div class="footSearch">
			<div id="subSearch">
				<input type="text" placeholder="검색어 입력" value="${pageVO.cri.search}" name="search" class="form-control">
				<a href="#" class="btn"><i class="lni lni-24 lni-search-1"></i></a>
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
	
	<div>
		<button class="btn" type="button" onclick="location.href='/product/register'" style="margin-top:20px;">상품 등록</button>
	</div>
	<div style="margin-top:20px;">
		<button class="btn" type="button" onclick="location.href='/home'">홈으로 돌아가기</button>
	</div>
</section>

<jsp:include page="/WEB-INF/views/include/Footer.jsp" />
