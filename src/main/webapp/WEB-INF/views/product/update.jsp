<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/views/include/Header.jsp" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>상품 수정</title>
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=&libraries=services"></script>

	<meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>

<style>
    body {
        font-family: 'Pretendard', sans-serif;
        background-color: #f8f9fa;
        margin: 0;
        padding: 10px;
    }

    .section {
        margin-bottom: 15px;
        padding: 15px;
        background-color: #fff;
        border-radius: 12px;
        box-shadow: 0 2px 10px rgba(0,0,0,0.05);
    }

    h1 {
        font-size: 20px;
        text-align: center;
        margin: 15px 0;
    }

    
    label {
        display: block;
        margin-bottom: 8px;
        font-weight: 600;
        color: #333;
    }
    input[type="text"], input[type="number"], textarea, select {
        width: 100%;
        padding: 10px;
        border: 1px solid #eee;
        border-radius: 8px;
        box-sizing: border-box;
        margin-bottom: 15px;
        font-family: 'Pretendard', sans-serif;
    }
    textarea {
        height: 150px;
        resize: vertical;
    }

   
    .btn {
        padding: 10px 20px;
        border-radius: 8px;
        font-weight: 600;
        border: none;
        cursor: pointer;
        transition: 0.2s;
        background-color: #ff8a3d;
        color: white;
        margin-right: 5px;
    }
    .btn:hover { opacity: 0.9; }
    .btn-cancel { background-color: #6c757d; }
    .btn-delete { background-color: #ff4d4d; }

    .img-preview {
        display: flex;
        flex-wrap: wrap;
        gap: 10px;
        margin-bottom: 15px;
    }
    .img-preview img {
        width: 100px;
        height: 100px;
        object-fit: cover;
        border-radius: 8px;
        border: 1px solid #eee;
    }
</style>
	
</head>
<body>

	<h1>상품 수정</h1>

	<form id="productForm" action="update" method="post" enctype="multipart/form-data">
		
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		
		<input type="hidden" name="productId" value="${product.productId}">

		<div>
			<label>상품명:</label>
			<input type="text" name="title" value="${product.title}" required>
		</div>

		<div>
			<label>가격:</label>
			<input type="number" name="price" value="${product.price}" required min="0">
		</div>

		<div>
			<label>상품 설명:</label>
			<textarea name="description" required>${product.description}</textarea>
		</div>

		<div>
			<label>거래 희망 지역:</label>
			<input type="text" id="location" name="location" value="${product.location}" readonly required>
			<button type="button" onclick="searchAddress()">주소 찾기</button>
		</div>

		<input type="hidden" id="lat" name="lat" value="${product.lat}">
		<input type="hidden" id="lon" name="lon" value="${product.lon}">
		
		<c:if test="${sessionScope.loginUser.id == product.sellerId}">
			<div>
				<label>판매 상태:</label>
				<select name="status">
					<option value="0" ${product.status == 0 ? 'selected' : ''}>판매중</option>
					<option value="1" ${product.status == 1 ? 'selected' : ''}>예약중</option>
					<option value="2" ${product.status == 2 ? 'selected' : ''}>거래완료</option>
				</select>
			</div>
		</c:if>
		
		<div>
		    <label>기존 이미지 목록:</label>
		    <c:forEach var="img" items="${product.images}">
		        <div>
		            <img src="/upload/${img.storedName}" width="120">
		            (${img.originName})
		        </div>
		    </c:forEach>
		</div>

		<div>
		    <label>이미지 변경(선택):</label><br>
		    <input type="file" name="uploadFiles" accept="image/*" multiple>
		</div>

		<hr>

		<c:if test="${not empty sessionScope.loginUser and sessionScope.loginUser.id == product.sellerId}">
	        <button type="submit" class="btn">수정 완료</button>
	        <a href="delete?productId=${product.productId}" onclick="return confirm('정말 삭제하시겠습니까?');" style="text-decoration: none;">
	            <button type="button" class="btn btn-delete">삭제</button>
	        </a>
	    </c:if>
	    
	    <button type="button" class="btn btn-cancel" onclick="history.back()">취소</button>
		

	</form>

	<script>
	    var geocoder;
	    kakao.maps.load(function() {
	        geocoder = new kakao.maps.services.Geocoder();
	    });
	
	    function searchAddress() {
	        new daum.Postcode({
	            oncomplete: function(data) {
	                var addr = data.address;
	                document.getElementById('location').value = addr;
	                geocoder.addressSearch(addr, function(results, status) {
	                    if (status === kakao.maps.services.Status.OK) {
	                        document.getElementById('lat').value = results[0].y;
	                        document.getElementById('lon').value = results[0].x;
	                    }
	                });
	            }
	        }).open();
	    }
	
	    document.getElementById("productForm").addEventListener("submit", function(e){
	        if(!document.getElementById('lat').value){
	            e.preventDefault();
	            alert("주소 검색을 완료해주세요.");
	        }
	    });
	</script>
	
	<jsp:include page="/WEB-INF/views/include/Footer.jsp" />
	
</body>
</html>
