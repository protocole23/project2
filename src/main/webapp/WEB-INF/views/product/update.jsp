<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/views/include/Header.jsp" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>상품 수정</title>
	<script src="//t1.daumcdn.net/mapjs/prod/service/postcode/5.0.0/postcode.js"></script>
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=e521fcb93dd80c8fe95035867771d15c&libraries=services"></script>

	<meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>

<style>
	body {
		font-family: 'Arial', sans-serif;
		background-color: #f7f7f7;
		margin: 0;
		padding: 0;
	}
	h1 {
		text-align: center;
		margin-top: 30px;
		color: #333;
	}
	form {
		background-color: #fff;
		max-width: 700px;
		margin: 40px auto;
		padding: 30px;
		border-radius: 10px;
		box-shadow: 0 0 15px rgba(0,0,0,0.1);
	}
	form div {
		margin-bottom: 20px;
	}
	label {
		display: block;
		margin-bottom: 6px;
		font-weight: bold;
		color: #555;
	}
	input[type="text"], input[type="number"], textarea, select {
		width: 100%;
		padding: 10px;
		border: 1px solid #ccc;
		border-radius: 6px;
		box-sizing: border-box;
		font-size: 14px;
	}
	textarea {
		height: 100px;
		resize: vertical;
	}
	button {
		padding: 10px 20px;
		border: none;
		border-radius: 6px;
		background-color: #0077ff;
		color: white;
		cursor: pointer;
		font-size: 14px;
		margin-right: 10px;
	}
	button[type="button"] {
		background-color: #888;
	}
	button:hover {
		opacity: 0.9;
	}
	hr {
		border: 0;
		height: 1px;
		background-color: #eee;
		margin: 30px 0;
	}
	.img-preview {
		display: flex;
		gap: 15px;
		flex-wrap: wrap;
	}
	.img-preview div {
		text-align: center;
	}
	.img-preview img {
		width: 120px;
		height: 120px;
		object-fit: cover;
		border-radius: 6px;
		border: 1px solid #ccc;
	}
</style>
	
</head>
<body>

	<h1>상품 수정</h1>

	<form action="update" method="post" enctype="multipart/form-data">
		
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		
		<input type="hidden" name="productId" value="${product.productId}">
		<input type="hidden" name="status" value="${product.status}">

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

		<hr>

		<div>
			<label>이미지 변경(선택):</label><br>
			<input type="file" name="file1" accept="image/*">
			<input type="file" name="file2" accept="image/*">
			<input type="file" name="file3" accept="image/*">
		</div>

		<hr>

		<c:if test="${sessionScope.memberId == product.sellerId}">
			<button type="submit">수정 완료</button>
			<a href="delete?productId=${product.productId}" onclick="return confirm('정말 삭제하시겠습니까?');">
				<button type="button">삭제</button>
			</a>
		</c:if>
		<button type="button" onclick="history.back()">취소</button>

	</form>

	<script>
	var geocoder = new kakao.maps.services.Geocoder();

	function searchAddress() {
		new daum.Postcode({
			oncomplete: function(data) {
				var addr = data.address;
				document.getElementById('location').value = addr;

				geocoder.addressSearch(addr, function(results, status) {
					if (status === kakao.maps.services.Status.OK) {
						var result = results[0];
						document.getElementById('lat').value = result.y;
						document.getElementById('lon').value = result.x;
					}
				});
			}
		}).open();
	}
	</script>
	
	<jsp:include page="/WEB-INF/views/include/Footer.jsp" />
	
</body>
</html>
