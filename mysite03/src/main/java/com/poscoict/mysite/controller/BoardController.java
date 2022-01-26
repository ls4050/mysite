package com.poscoict.mysite.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.poscoict.mysite.security.Auth;
import com.poscoict.mysite.service.BoardService;
import com.poscoict.mysite.vo.BoardVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;

	@RequestMapping("")
	public String list(Integer page, String keyword, Model model) {
		Map<String, Object> m = boardService.getContentsList(page, keyword);
		model.addAttribute("m", m);
		return "board/list";
	}
	
	@Auth
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write(Integer page, String keyword, Model model) {
		System.out.println(page+":"+keyword);
		model.addAttribute("page", page);
		model.addAttribute("keyword", keyword);
		return "board/write";
	}

	@Auth
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(BoardVo vo) {
		boardService.addContents(vo);
		return "redirect:/board";
	}

	@RequestMapping("/view/{no}")
	public String view(@PathVariable("no") Long no, Integer page, String keyword, Model model) {
		model.addAttribute("vo", boardService.getContents(no));
		model.addAttribute("page", page);
		model.addAttribute("keyword", keyword);
		return "board/view";
	}
	
	@Auth
	@RequestMapping("/delete/{no}")
	public String delete(@PathVariable("no") Long no) {
		boardService.deleteContents(no);
		return "redirect:/board";
	}
	
	@Auth
	@RequestMapping(value = "/update/{no}", method = RequestMethod.GET)
	public String update(@PathVariable("no") Long no, Model model) {
		model.addAttribute("vo", boardService.getContents(no));
		return "board/update";
	}
	
	@Auth
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(BoardVo vo) {
		boardService.updateContents(vo);
		return "redirect:/board/view/"+vo.getNo();
	}
	
	@Auth
	@RequestMapping(value = "/reply/{no}", method = RequestMethod.GET)
	public String reply(@PathVariable("no") Long no, Model model) {
		model.addAttribute("vo", boardService.getContents(no));
		return "board/write";
	}

}
