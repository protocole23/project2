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
	background-color: #f9f9f9;
	margin: 20px;
}

h2 {
	margin-bottom: 20px;
}

.room-list {
	list-style: none;
	padding: 0;
	max-width: 500px;
	margin: 0 auto;
}

.room-list li {
	padding: 12px;
	margin: 8px 0;
	background-color: #fff;
	border: 1px solid #ddd;
	border-radius: 6px;
	cursor: pointer;
	transition: background-color 0.2s;
}

.room-list li:hover {
	background-color: #f0f0f0;
}

.room-info {
	display: flex;
	justify-content: space-between;
	align-items: center;
}
</style>
</head>
<body>

<h2>My Chat Rooms</h2>

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

</body>
</html>
