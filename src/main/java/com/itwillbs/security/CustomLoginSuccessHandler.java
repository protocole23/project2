package com.itwillbs.security;

import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import com.itwillbs.domain.UserVO;
import com.itwillbs.persistence.UserDAO; // 본인의 DAO 경로 확인

public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Inject
    private UserDAO dao;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        
        HttpSession session = request.getSession();
        String userid = authentication.getName(); // 시큐리티가 확인한 아이디

        try {
            // DB에서 유저 전체 정보를 가져와 세션에 저장 (id(int) 값을 채팅방에서 써야 하므로 중요)
            UserVO loginUser = dao.getUser(userid); 
            session.setAttribute("loginUser", loginUser);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 로그인 성공하면 무조건 홈으로 이동
        response.sendRedirect("/home");
    }
}