package com.itwillbs.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.itwillbs.domain.OrderVO;
import com.itwillbs.service.OrderService;

@Controller
@RequestMapping("/order/*")
public class OrderController {

	@Inject
	private OrderService service;

	@PostMapping("/create")
	public String create(OrderVO vo) throws Exception {
		service.createOrder(vo);
		return "redirect:/order/list";
	}

	@GetMapping("/list")
	public String list(Model model) throws Exception {
		model.addAttribute("orderList", service.getOrderList());
		return "/order/orderList";
	}

	@GetMapping("/detail")
	public String detail(int orderId, Model model) throws Exception {
		model.addAttribute("order", service.getOrder(orderId));
		return "/order/orderDetail";
	}

	@PostMapping("/status")
	public String status(int orderId, int status) throws Exception {
		service.changeStatus(orderId, status);
		return "redirect:/order/detail?orderId=" + orderId;
	}
}