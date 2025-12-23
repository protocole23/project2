<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/views/include/Header.jsp" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>상품 수정</title>
	<script src="//t1.daumcdn.net/mapjs/prod/service/postcode/5.0.0/postcode.js"></script>
	<script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=460a77fbaeeaaafcfd951a99cdbe4767&libraries=services"></script>
</head>
<body>

	<h1>상품 수정</h1>

	<form action="update" method="post" enctype="multipart/form-data">

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
		
		<c:if test="${sessionScope.userId == product.sellerId}">
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
