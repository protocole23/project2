package com.itwillbs.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 *    CustomAccessDeniedHandler 
 *    => 접근권한 없이 접근하는 경우 처리하는 객체
 */

public class CustomAccessDeniedHandler implements AccessDeniedHandler{
	
	//mylog
	private static final Logger logger 
		= LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

	
	@Override
	public void handle(HttpServletRequest request,
			HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		logger.info(" 접근권한이 없이 접근하는 경우 실행! ");
		logger.info(" handle() 실행 ");
		
		logger.info(" 해당처리 동작 실행 => /accessError를 호출 ");
		
		// 에러 페이지(뷰) 호출
		response.sendRedirect("/accessError");
		
	}
	

}
