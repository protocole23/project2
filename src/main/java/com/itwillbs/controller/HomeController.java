package com.itwillbs.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.itwillbs.domain.UserVO;
import com.itwillbs.domain.ProductVO;
import com.itwillbs.domain.NoticeVO;
import com.itwillbs.service.UserService;
import com.itwillbs.service.ProductService;
import com.itwillbs.service.NoticeService;

@Controller
public class HomeController {

    @Inject
    private UserService userService;

    @Inject
    private ProductService productService;

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String home(Model model, HttpSession session) throws Exception {

    	// 로그인 사용자 정보 확인
        String userid = (String) session.getAttribute("userid");
        if (userid != null) {
            UserVO loginUser = userService.getUser(userid);
            model.addAttribute("loginUser", loginUser);
        }

        // 최신 상품 리스트
        List<ProductVO> latestProducts = productService.getRecentProducts();
        model.addAttribute("latestProducts", latestProducts);

        

        return "home";
    }
}
