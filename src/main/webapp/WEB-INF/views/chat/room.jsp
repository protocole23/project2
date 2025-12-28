<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Chat Room</title>

<meta name="_csrf" content="${_csrf.token}">
<meta name="_csrf_header" content="${_csrf.headerName}">

<style>
body {
	font-family: Arial, sans-serif;
	background: linear-gradient(135deg, #f5f7fa, #e4e7eb);
	margin: 0;
	padding: 0;
}

.chat-container {
	width: 420px;
	margin: 40px auto;
	border-radius: 12px;
	background-color: #fff;
	box-shadow: 0 8px 20px rgba(0, 0, 0, 0.08);
	display: flex;
	flex-direction: column;
	overflow: hidden;
}

.chat-header {
	padding: 12px;
	background-color: #4a6cf7;
	color: #fff;
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.chat-header button {
	background-color: #fff;
	color: #4a6cf7;
	border: none;
	padding: 6px 12px;
	border-radius: 6px;
	cursor: pointer;
	font-size: 13px;
}

.chat-header button:hover {
	background-color: #eef1ff;
}

.chat-messages {
	flex: 1;
	padding: 12px;
	overflow-y: auto;
	background-color: #f9fafb;
}

.chat-message {
	margin-bottom: 10px;
	max-width: 75%;
	word-break: break-word;
}

.chat-message.me {
	margin-left: auto;
	text-align: right;
}

.chat-message.other {
	margin-right: auto;
	text-align: left;
}

.chat-message.me div:last-child {
	background-color: #4a6cf7;
	color: #fff;
}

.chat-message.other div:last-child {
	background-color: #e5e7eb;
	color: #000;
}

.chat-nick {
	font-size: 11px;
	margin-bottom: 3px;
	color: #555;
}

.chat-message div:last-child {
	padding: 8px 10px;
	border-radius: 10px;
	display: inline-block;
	font-size: 14px;
}

.chat-input {
	display: flex;
	border-top: 1px solid #ddd;
}

.chat-input input {
	flex: 1;
	padding: 12px;
	border: none;
	outline: none;
	font-size: 14px;
}

.chat-input button {
	width: 90px;
	border: none;
	background-color: #4a6cf7;
	color: #fff;
	cursor: pointer;
	font-size: 14px;
}

.chat-input button:hover {
	background-color: #3957d6;
}
</style>

<script>
	

const roomId = Number("${roomId}");
const myId = Number("${sessionScope.loginUser.id}");
const myNick = "${sessionScope.loginUser.name}";

// WebSocket 연결
const socket = new WebSocket("ws://" + location.host + "/ws/chat?roomId=" + roomId);

socket.onmessage = function(event) {
    const msg = JSON.parse(event.data);
    
    if (msg.senderId !== myId) {
        appendMessage(msg.senderNick, msg.message, false);
    }
};

function sendMessage() {
    const input = document.getElementById("messageInput");
    const text = input.value.trim();
    if (text === "") return;

    // 1. WebSocket 전송
    const msg = {
        roomId: roomId,
        senderId: myId,
        senderNick: myNick,
        message: text
    };
    if (socket.readyState === WebSocket.OPEN) {
        socket.send(JSON.stringify(msg));
    }

    // 2. DB 저장
    const formData = new URLSearchParams();
    formData.append("roomId", roomId);
    formData.append("message", text);

    
    fetch("/chat/send", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: formData
    })
    .then(res => {
        if(!res.ok) throw new Error("서버 에러: " + res.status);
        return res.text();
    })
    .then(data => {
        console.log("DB 저장 완료:", data);
        appendMessage(myNick, text, true);
        input.value = "";
    })
    .catch(err => {
        console.error("전송 에러:", err);
    });
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

<jsp:include page="/WEB-INF/views/include/Header.jsp" />

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
				<div>${msg.message}</div>
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

<jsp:include page="/WEB-INF/views/include/Footer.jsp" />

</body>
</html>
