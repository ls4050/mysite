package com.poscoict.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
	@RequestMapping({"", "/main"})
	public String index() {
//		return "/WEB-INF/views/main/index.jsp";
		return "main/index";
	}
}