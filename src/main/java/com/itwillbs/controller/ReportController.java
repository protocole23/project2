package com.itwillbs.controller;

import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.itwillbs.domain.ReportVO;
import com.itwillbs.service.ReportService;

@Controller
@RequestMapping("/report/*")
public class ReportController {

	@Inject
	private ReportService service;

	@GetMapping("/list")
	public String list(Model model) throws Exception {
		List<ReportVO> list = service.getReportList();
		model.addAttribute("list", list);
		return "/report/list";
	}

	@PostMapping("/create")
	public String create(ReportVO vo) throws Exception {
		service.createReport(vo);
		return "redirect:/report/list";
	}

	@GetMapping("/detail")
	public String detail(@RequestParam("id") int id, Model model) throws Exception {
		model.addAttribute("report", service.getReport(id));
		return "/report/detail";
	}
}