<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Chat Rooms</title>
<style>
body {
	font-family: Arial, sans-serif;
	background: linear-gradient(135deg, #f5f7fa, #e4e7eb);
	margin: 0;
	padding: 0;
}

h2 {
	text-align: center;
	margin: 30px 0 20px;
	color: #333;
}

.room-list {
	list-style: none;
	padding: 0;
	max-width: 420px;
	margin: 0 auto 40px;
}

.room-list li {
	padding: 14px;
	margin-bottom: 12px;
	background-color: #fff;
	border-radius: 10px;
	box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
	cursor: pointer;
	transition: transform 0.2s, box-shadow 0.2s;
}

.room-list li:hover {
	transform: translateY(-3px);
	box-shadow: 0 8px 18px rgba(0, 0, 0, 0.12);
}

.room-info {
	display: flex;
	justify-content: space-between;
	align-items: center;
	font-size: 14px;
	color: #444;
}

.room-info span:first-child {
	font-weight: bold;
	color: #4a6cf7;
}
</style>
</head>
<body>

<jsp:include page="/WEB-INF/views/include/Header.jsp" />

<h2>채팅방 목록</h2>

<ul class="room-list">
	<c:forEach var="room" items="${rooms}">
		<li onclick="enterRoom(${room.roomId})">
			<div class="room-info">
				<span>Room ID: ${room.roomId}</span>
				<span>Product ID: ${room.productId}</span>
			</div>
		</li>
	</c:forEach>
</ul>

<script>
function enterRoom(roomId) {
	location.href = '/chat/room?roomId=' + roomId;
}
</script>

<jsp:include page="/WEB-INF/views/include/Footer.jsp" />

</body>
</html>
