<%@ page contentType="text/html; charset=UTF-8" %>
<jsp:include page="/WEB-INF/views/include/Header.jsp" />

<h2>로그인</h2>

<form action="${pageContext.request.contextPath}/user/login" method="post">
	<input type="text" name="userid" placeholder="아이디">
	<input type="password" name="userpw" placeholder="비밀번호">
	<button type="submit">로그인</button>
</form>

<a href="${pageContext.request.contextPath}/user/join">회원가입</a>

<jsp:include page="/WEB-INF/views/include/Footer.jsp" />