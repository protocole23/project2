package com.itwillbs.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.jaas.AuthorityGranter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * CustomLoginSuccessHandler 
 *  => 사용자 로그인 성공시 처리하는 객체
 */
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler{
	
	private static final Logger logger = LoggerFactory.getLogger(CustomLoginSuccessHandler.class);
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		logger.info(" onAuthenticationSuccess() 실행! ");
		logger.info(" 사용자 로그인 성공시 실행! ");
		
		// 사용자의 권한정보를 저장
		// -> 사용자에 따라서 권한이 여러개 일수 있기 때문
		List<String> roleNames = new ArrayList<>();
		
		authentication.getAuthorities().forEach(authority -> {
			// 권한정보를 배열에 저장(반복)
			roleNames.add(authority.getAuthority());
		});
		
//		authentication.getAuthorities().forEach(new Consumer<GrantedAuthority>() {
//			@Override
//			public void accept(GrantedAuthority authority) {
//				roleNames.add(authority.getAuthority());
//			}
//		});
		
		logger.info(" roleNames : {}",roleNames);
		
		//roleNames : [ROLE_ADMIN, ROLE_MEMBER]
		
		//roleNames.get(0).equals("itwill")
		// 권한에 따른 사용자 페이지 접근 제어
		if(roleNames.contains("ROLE_ADMIN")) {
			logger.info(" 관리자 권한을 포함한 사용자가 로그인 성공! ");
			
			// 관리자 페이지로 이동
			response.sendRedirect("/admin");
			// 함수 종료(다른 동작 실행X)
			return; 
		}
		
		if(roleNames.contains("ROLE_MEMBER")) {
			logger.info("멤버 권한을 포함한 사용자가 로그인 성공! ");
			// 멤버 페이지로 이동
			response.sendRedirect("/member");
			// 함수 종료(다른 동작 실행X)
			return;
		}
		
		// 아무런 권한이 없는경우
		logger.info(" 아무런 권한이 없는 사용자가 로그인 성공! ");
		// all 페이지로 이동
		response.sendRedirect("/all");		

		
	}

	
	
}
