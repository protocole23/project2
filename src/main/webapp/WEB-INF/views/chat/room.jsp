<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Chat Room</title>

<meta name="_csrf" content="${_csrf.token}">
<meta name="_csrf_header" content="${_csrf.headerName}">

<style>
.chat-container {
	width: 500px;
	margin: 30px auto;
	border: 1px solid #ccc;
	display: flex;
	flex-direction: column;
}

.chat-header {
	padding: 10px;
	border-bottom: 1px solid #ccc;
	display: flex;
	justify-content: space-between;
}

.chat-messages {
	flex: 1;
	padding: 10px;
	overflow-y: auto;
	border-bottom: 1px solid #ccc;
}

.chat-message {
	margin-bottom: 8px;
}

.chat-message.me {
	text-align: right;
	color: blue;
}

.chat-message.other {
	text-align: left;
	color: black;
}

.chat-nick {
	font-size: 12px;
	color: #666;
}

.chat-input {
	display: flex;
}

.chat-input input {
	flex: 1;
	padding: 10px;
	border: none;
	border-top: 1px solid #ccc;
}

.chat-input button {
	width: 80px;
}
</style>

<script>
	/* ===== 서버에서 내려준 값 ===== */
	const roomId = ${roomId};
	const myId = ${sessionScope.loginUser.id};
	const myNick = "${sessionScope.loginUser.nickname}";

	/* ===== WebSocket 연결 (JSON 사용) ===== */
	const socket = new WebSocket("ws://" + location.host + "/ws/chat?roomId=" + roomId);

	socket.onmessage = function(event) {
		const msg = JSON.parse(event.data);

		const isMe = msg.senderId === myId;
		appendMessage(msg.senderNick, msg.content, isMe);
	};

	/* ===== 메시지 전송 ===== */
	function sendMessage() {
		const input = document.getElementById("messageInput");
		const text = input.value.trim();

		if (text === "") return;

		const msg = {
			roomId: roomId,
			senderId: myId,
			senderNick: myNick,
			content: text
		};

		/* WebSocket 전송 */
		socket.send(JSON.stringify(msg));

		/* DB 저장 */
		const csrfToken = document.querySelector('meta[name="_csrf"]').content;
		const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;
		
		fetch("/chat/send", {
			method: "POST",
			headers: {
				"Content-Type": "application/x-www-form-urlencoded",
				[csrfHeader]: csrfToken
			},
			body: "roomId=" + roomId + "&content=" + encodeURIComponent(text)
		});

		appendMessage(myNick, text, true);
		input.value = "";
	}

	/* ===== 메시지 출력 ===== */
	function appendMessage(nick, text, isMe) {
		const box = document.getElementById("chatBox");

		const wrap = document.createElement("div");
		wrap.className = "chat-message " + (isMe ? "me" : "other");

		const nickDiv = document.createElement("div");
		nickDiv.className = "chat-nick";
		nickDiv.innerText = nick;

		const msgDiv = document.createElement("div");
		msgDiv.innerText = text;

		wrap.appendChild(nickDiv);
		wrap.appendChild(msgDiv);
		box.appendChild(wrap);

		box.scrollTop = box.scrollHeight;
	}

	/* ===== 채팅방 나가기 ===== */
	function leaveRoom() {
		socket.close();
		location.href = "/chat/rooms";
	}
</script>
</head>

<body>

<div class="chat-container">

	<!-- 헤더 -->
	<div class="chat-header">
		<button onclick="leaveRoom()">← 목록</button>
		<button onclick="leaveRoom()">나가기</button>
	</div>

	<!-- 메시지 영역 -->
	<div class="chat-messages" id="chatBox">
		<c:forEach var="msg" items="${messages}">
			<div class="chat-message ${msg.senderId == sessionScope.loginUser.id ? 'me' : 'other'}">
				<div class="chat-nick">${msg.senderNick}</div>
				<div>${msg.content}</div>
			</div>
		</c:forEach>
	</div>

	<!-- 입력 -->
	<div class="chat-input">
		<input type="text" id="messageInput"
			placeholder="메시지 입력"
			onkeydown="if(event.key === 'Enter') sendMessage();">
		<button onclick="sendMessage()">전송</button>
	</div>

</div>

</body>
</html>
