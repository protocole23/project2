<%@ page contentType="text/html; charset=UTF-8" %>
<jsp:include page="/WEB-INF/views/include/Header.jsp" />

<style>
    body {
	font-family: Arial, sans-serif;
	background-color: #f5f5f5;
	margin: 0;
	padding: 0;
	display: flex;
	flex-direction: column;
	min-height: 100vh;
}

.content-wrapper {
	flex: 1;
	display: flex;
	justify-content: center;
	align-items: center;
	padding: 20px;
	box-sizing: border-box;
}
.login-container {
	background-color: #fff;
	width: 100%;
	max-width: 400px;
	min-width: 320px;
	padding: 35px;
	border-radius: 10px;
	box-shadow: 0 6px 20px rgba(0,0,0,0.1);
	box-sizing: border-box;
	text-align: center;
}

.login-container h2 {
	margin-bottom: 25px;
	color: #333;
	font-size: 24px;
}

.login-container input[type="text"],
.login-container input[type="password"] {
	width: 100%;
	padding: 12px 15px;
	margin: 8px 0;
	border: 1px solid #ccc;
	border-radius: 6px;
	font-size: 14px;
	box-sizing: border-box;
}

.login-container button {
	width: 100%;
	padding: 12px;
	margin-top: 10px;
	border: none;
	border-radius: 6px;
	cursor: pointer;
	font-weight: bold;
	font-size: 16px;
	transition: background-color 0.3s;
}

.login-container button[type="submit"] {
	background-color: #007BFF;
	color: #fff;
}

.login-container button[type="submit"]:hover {
	background-color: #0069d9;
}

.login-container button[type="button"] {
	background-color: #28A745;
	color: #fff;
}

.login-container button[type="button"]:hover {
	background-color: #218838;
}
</style>

<div class="content-wrapper">
	<div class="login-container">
		<h2>로그인</h2>
	
		<form action="${pageContext.request.contextPath}/user/login" method="post">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			
			<input type="text" name="userid" placeholder="아이디">
			<input type="password" name="userpw" placeholder="비밀번호">
			<button type="submit">로그인</button>
		</form>
	
		<button type="button" onclick="location.href='${pageContext.request.contextPath}/user/join'">
			회원가입
		</button>
	</div>
</div>

<jsp:include page="/WEB-INF/views/include/Footer.jsp" />