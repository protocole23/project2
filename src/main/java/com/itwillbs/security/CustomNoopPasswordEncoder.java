package com.itwillbs.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * CustomNoopPasswordEncoder
 * => 임시로 비밀번호 인코딩 없이 처리 
 * 
 * NoOpPasswordEncoder
 *  => 스프링 시큐리티 4.xx에서는 제공
 *     5.xx 부터는 사용X
 * 
 */
public class CustomNoopPasswordEncoder implements PasswordEncoder{
	
	private static final Logger logger = LoggerFactory.getLogger(CustomNoopPasswordEncoder.class);

	
	@Override
	public String encode(CharSequence rawPassword) {
		logger.info(" encode() 실행! ");
		logger.info(" 암호화 처리하는 메서드! ");
		
		// 암호화 처리 알고리즘 
		// A -> 7, B->e ......
		logger.info(" 암호화 처리중..... ");

		return rawPassword.toString();
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		logger.info(" matches() 실행! ");
		logger.info(" 원본 비밀번호와 암호화된 비밀번호를 비교 메서드");

		return rawPassword.toString().equals(encodedPassword);
	}
	
}
