<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/WEB-INF/views/include/Header.jsp" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${product.title} - 상품 상세</title>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=&libraries=services"></script>
<style>
    body {
    font-family: 'Pretendard', sans-serif;
    background-color: #f8f9fa;
    margin: 0;
    padding: 10px; 
}


.section, .section1, .section2, .section3 {
    margin-bottom: 15px;
    padding: 15px;
    background-color: #fff;
    border-radius: 12px;
    box-shadow: 0 2px 10px rgba(0,0,0,0.05);
}


.images {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
    justify-content: flex-start;
}

.images h3 {
    width: 100%;
    margin-top: 0;
    font-size: 16px;
}

.images img {
    
    max-width: 48%;
    max-height: 350px;
    width: auto;
    height: auto;
    object-fit: cover;
    border-radius: 8px;
    border: 1px solid #eee;
    transition: transform 0.2s;
}

.images img:hover {
    transform: scale(1.02);
}


#map {
    width: 100%;
    height: 250px;
    border-radius: 8px;
}


h1 {
    font-size: 20px;
    text-align: center;
    margin: 15px 0;
}


.btn {
    padding: 10px 20px;
    border-radius: 8px;
    font-weight: 600;
    border: none;
    cursor: pointer;
    transition: 0.2s;
}


@media (max-width: 480px) {
    .images img {
        max-width: 100%;
        max-height: 250px;
    }
}

</style>
</head>
<body>

<h1>상품 상세 정보</h1>

<div class="section">
    <h2>${product.title}</h2>
</div>

<div class="section images">
    <h3>상품 이미지</h3>
    <c:choose>
        <c:when test="${not empty product.images}">
            <c:forEach var="img" items="${product.images}">
			    <img src="/product/image/${img.storedName}" alt="${product.title}">
			</c:forEach>
        </c:when>
        <c:otherwise>
            <img src="/resources/img/default.png" alt="기본 이미지">
        </c:otherwise>
    </c:choose>
</div>

<div class="section1">
    <div class="info-left">
        <p><strong>가격:</strong> <fmt:formatNumber value="${product.price}" pattern="#,###"/> 원</p>
        <p>
            <strong>판매 상태:</strong>
            <c:choose>
                <c:when test="${product.status eq 0}">판매 중</c:when>
                <c:when test="${product.status eq 1}">예약 중</c:when>
                <c:when test="${product.status eq 2}">거래 완료</c:when>
                <c:otherwise>알 수 없음</c:otherwise>
            </c:choose>
        </p>
    </div>
    <div class="info-right">
        <p><strong>거래 희망 지역:</strong> ${product.location}</p>
        <div id="map"></div>
        <p><strong>등록일:</strong> <fmt:formatDate value="${product.createdAt}" pattern="yyyy-MM-dd HH:mm"/></p>
    </div>
</div>

<div class="section2">
    <h3>상세 설명</h3>
    <p>${product.description}</p>
</div>

<div class="section3">
    <h3>판매자 정보</h3>
    <p><strong>판매자 ID:</strong> ${product.sellerId}</p>
</div>

<div class="section">
    <c:if test="${not empty sessionScope.loginUser and sessionScope.loginUser.id == product.sellerId}">
        <button class="btn" onclick="location.href='update?productId=${product.productId}'">수정</button>
        <button class="btn" onclick="if(confirm('정말로 삭제하시겠습니까?')) { location.href='delete?productId=${product.productId}'; }">삭제</button>
    </c:if>

    <c:if test="${not empty sessionScope.loginUser 
                and sessionScope.loginUser.id != product.sellerId 
                and product.status eq 0}">
        <button class="btn" onclick="location.href='/chat/start?productId=${product.productId}'">
            판매자와 채팅하기
        </button>
    </c:if>

    <c:if test="${empty sessionScope.loginUser}">
        <p style="color: #666; font-size: 14px;">로그인 후 채팅이 가능합니다.</p>
    </c:if>

    <button class="btn" type="button" onclick="location.href='productlist'">목록으로</button>
</div>

    <script>
	    kakao.maps.load(function() {
	        var latStr = '${product.lat}';
	        var lonStr = '${product.lon}';
	
	        console.log("DB에서 가져온 좌표:", latStr, lonStr); 
	
	        var container = document.getElementById('map');
	        
	        // 1. 데이터 존재 여부 및 유효성 체크
	        if (latStr && lonStr && latStr.trim() !== "" && !isNaN(latStr)) {
	            var lat = parseFloat(latStr);
	            var lon = parseFloat(lonStr);
	
	            var options = {
	                center: new kakao.maps.LatLng(lat, lon),
	                level: 3
	            };
	            
	            // 2. 지도 생성
	            var map = new kakao.maps.Map(container, options);
	            
	            // 3. 마커 표시
	            var markerPosition = new kakao.maps.LatLng(lat, lon); 
	            var marker = new kakao.maps.Marker({
	                position: markerPosition
	            });
	            marker.setMap(map);
	            
	            console.log("지도 그리기 성공");
	        } else {
	             console.error("좌표 데이터가 없어서 지도를 그릴 수 없습니다.");
	             container.style.display = 'none';
	        }
	    });
	</script>
    
    <jsp:include page="/WEB-INF/views/include/Footer.jsp" />
    
</body>
</html>