package com.itwillbs.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.itwillbs.domain.NoticeVO;
import com.itwillbs.service.NoticeService;

@Controller
@RequestMapping("/notice/*")
public class NoticeController {

	@Inject
	private NoticeService service;

	@GetMapping("/list")
	public String list(Model model) throws Exception {
		List<NoticeVO> list = service.getNoticeList();
		model.addAttribute("list", list);
		return "/notice/list";
	}

	@GetMapping("/detail")
	public String detail(@RequestParam("id") int id, Model model) throws Exception {
		NoticeVO vo = service.getNotice(id);
		model.addAttribute("notice", vo);
		return "/notice/detail";
	}

	@GetMapping("/write")
	public String writeForm() throws Exception {
		return "/notice/write";
	}

	@PostMapping("/write")
	public String write(NoticeVO vo) throws Exception {
		service.insertNotice(vo);
		return "redirect:/notice/list";
	}

	@PostMapping("/edit")
	public String edit(NoticeVO vo) throws Exception {
		service.updateNotice(vo);
		return "redirect:/notice/detail?id=" + vo.getNoticeId();
	}

	@GetMapping("/delete")
	public String delete(@RequestParam("id") int id) throws Exception {
		service.deleteNotice(id);
		return "redirect:/notice/list";
	}
}
