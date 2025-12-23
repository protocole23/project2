<%@ page contentType="text/html; charset=UTF-8" %>
<jsp:include page="/WEB-INF/views/include/Header.jsp" />

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

<script>
	$(document).ready(function() {

		var $form = $('.form-join');

		var $pwd = $('#pwd');
		var $pwdConfirm = $('#pwdConfirm');
		var $pwdError = $('#pwdError');

		var phone = $('#phone');
		var phoneError = $('#phoneError');

		var isUseridChecked = false;
		var idMsg = $('#useridCheckMsg');

		function validatePassword() {
			var pwd = $pwd.val();
			var pwdConfirm = $pwdConfirm.val();
			var pwdError = [];

			if (pwd.length < 8) {
				pwdError.push('비밀번호는 최소 8자 이상이어야 합니다.');
			}

			if (!/[A-Za-z]/.test(pwd)) {
				pwdError.push('비밀번호에는 영문자가 최소 하나 포함되어야 합니다.');
			}

			if (!/[0-9]/.test(pwd)) {
				pwdError.push('비밀번호에는 숫자가 최소 하나 포함되어야 합니다.');
			}

			if (!/[!@#$%^&*(),.?":{}|<>]/.test(pwd)) {
				pwdError.push('비밀번호에는 특수문자 하나 이상 포함되어야 합니다.');
			}

			if (pwd !== pwdConfirm) {
				pwdError.push('비밀번호와 확인용 비밀번호가 일치하지 않습니다.');
			}

			if (pwdError.length > 0) {
				$pwdError.html(pwdError.join('<br>')).show();
				return false;
			} else {
				$pwdError.hide();
				return true;
			}
		}

		function validatePhone() {
			var phoneVal = phone.val().trim();
			var regex = /^\d+$/;

			if (!regex.test(phoneVal)) {
				phoneError.text("'-' 없이 숫자만 입력해 주세요.").show();
				return false;
			}

			if (phoneVal.length !== 11) {
				phoneError.text("11자의 핸드폰 번호를 입력해주세요.").show();
				return false;
			}

			phoneError.hide();
			return true;
		}

		$('#checkID').click(function() {
			var userid = $('#userid').val().trim();

			if (userid === '') {
				idMsg.css('color', 'red').text('아이디를 입력해주세요.');
				isUseridChecked = false;
				return;
			}

			$.ajax({
				type: 'GET',
				url: '${pageContext.request.contextPath}/user/idCheck',
				data: { userid: userid },
				dataType: 'json'
			}).done(function(res) {
				if (res.available) {
					idMsg.css('color', 'green').text('사용 가능한 아이디입니다.');
					isUseridChecked = true;
				} else {
					idMsg.css('color', 'red').text('이미 사용중인 아이디입니다.');
					isUseridChecked = false;
				}
			}).fail(function() {
				idMsg.css('color', 'red').text('아이디 중복검사 중 오류가 발생했습니다.');
				isUseridChecked = false;
			});
		});

		$('#userid').on('keyup change', function() {
			isUseridChecked = false;
			idMsg.text('');
		});

		$pwd.on('keyup change', validatePassword);
		$pwdConfirm.on('keyup change', validatePassword);
		phone.on('keyup change', validatePhone);

		$form.on('submit', function(e) {

			if (!isUseridChecked) {
				e.preventDefault();
				alert('아이디 중복검사를 해주세요.');
				return;
			}

			if (!validatePassword()) {
				e.preventDefault();
				alert('비밀번호 입력을 확인해주세요.');
				return;
			}

			if (!validatePhone()) {
				e.preventDefault();
				alert('핸드폰 번호 입력을 확인해주세요.');
				return;
			}
		});
	});
</script>

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

/* 공통 콘텐츠 래퍼 */
.content-wrapper {
	flex: 1; /* 푸터를 아래로 밀지 않도록 */
	display: flex;
	justify-content: center;
	align-items: center;
	padding: 20px;
	box-sizing: border-box;
}

.form-join {
	background-color: #fff;
	width: 100%;
	max-width: 500px;
	min-width: 320px;
	padding: 40px;
	border-radius: 10px;
	box-shadow: 0 6px 20px rgba(0,0,0,0.1);
	box-sizing: border-box;
	text-align: center;
}

.form-join h2 {
	text-align: center;
	margin-bottom: 30px;
	color: #333;
	font-size: 24px;
}

.form-join input[type="text"],
.form-join input[type="password"],
.form-join input[type="email"],
.form-join input[type="tel"] {
	width: 100%;
	padding: 12px 15px;
	margin: 8px 0;
	border: 1px solid #ccc;
	border-radius: 6px;
	box-sizing: border-box;
	font-size: 14px;
}

/* 아이디 중복검사 섹션 */
#userid-section {
	display: flex;
	align-items: center;
	margin-bottom: 5px;
}

#userid-section input {
	flex: 1;
}

#checkID {
	padding: 12px 20px;
	background-color: #ffc107;
	color: #fff;
	border: none;
	border-radius: 6px;
	cursor: pointer;
	font-weight: bold;
	transition: background-color 0.3s;
	margin-left: 0; /* 왼쪽으로 붙이기 */
	float: left; /* 버튼을 왼쪽으로 이동 */
}

#checkID:hover {
	background-color: #e0a800;
}

/* 오류 메시지 */
#errorMsg,
#pwdError,
#phoneError,
#useridCheckMsg {
	color: red;
	font-size: 13px;
	margin-top: 5px;
	word-break: break-word;
	text-align: left;
}

/* 가입 버튼 */
.form-join button[type="submit"] {
	background-color: #28a745;
	color: #fff;
	padding: 12px;
	width: 100%;
	margin-top: 15px;
	border: none;
	border-radius: 6px;
	cursor: pointer;
	font-weight: bold;
	font-size: 16px;
	transition: background-color 0.3s;
}

.form-join button[type="submit"]:hover {
	background-color: #218838;
}

/* =========================
   미디어 쿼리 - 모바일 최적화
========================= */
@media (max-width: 480px) {
	.login-container,
	.form-join {
		padding: 25px 15px;
		width: 95%;
	}
	.login-container h2,
	.form-join h2 {
		font-size: 20px;
	}
	#checkID {
		padding: 10px 15px;
		margin-left: 5px;
		font-size: 13px;
	}
}
</style>

<div class="content-wrapper">

	<form class="form-join" action="${pageContext.request.contextPath}/user/join" method="post" class="form-join">
		<h2>회원가입</h2>
	
		<input type="hidden"
			name="${_csrf.parameterName}"
			value="${_csrf.token}">
	
		<input type="text" id="userid" name="userid" placeholder="아이디" required>
		<button type="button" id="checkID">중복검사</button>
		<div id="useridCheckMsg"></div>
	
		<input type="password" id="pwd" name="userpw" placeholder="비밀번호" required>
		<input type="password" id="pwdConfirm" placeholder="비밀번호 확인" required>
		<div id="pwdError"></div>
	
		<input type="email" name="email" placeholder="이메일" required>
		<input type="text" name="name" placeholder="이름" required>
	
		<input type="tel" id="phone" name="phone" placeholder="전화번호" maxlength="11" required>
		<div id="phoneError"></div>
	
		<input type="text" name="addr" placeholder="주소" required>
	
		<button type="submit">가입</button>
	</form>
</div>

<jsp:include page="/WEB-INF/views/include/Footer.jsp" />
