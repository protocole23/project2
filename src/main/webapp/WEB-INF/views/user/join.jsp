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

<h2>회원가입</h2>

<form action="${pageContext.request.contextPath}/user/join" method="post" class="form-join">

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

<jsp:include page="/WEB-INF/views/include/Footer.jsp" />
