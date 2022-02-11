package com.poscoict.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.poscoict.mysite.service.GuestbookService;
import com.poscoict.mysite.vo.GuestbookVo;
import com.poscoict.mysite.vo.UserVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {
	
	@Autowired
	private GuestbookService guestbookService;
	
	@Autowired
	private HttpSession session;
	
	@RequestMapping("")
	public String index(Model model) {
		List<GuestbookVo> list = guestbookService.getMessageList();
		model.addAttribute("list", list);
		return "guestbook/index";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(GuestbookVo vo) {
		guestbookService.addMessage(vo);
		return "redirect:/guestbook";
	}

	@RequestMapping("/delete/{no}")
	public String delete(@ModelAttribute @PathVariable("no") Long no) {
		UserVo vo = (UserVo) session.getAttribute("authUser");
		
		if(vo!=null) {
			if("ADMIN".equals(vo.getRole())) {
				guestbookService.deleteMessageByAdmin(no);
				return "redirect:/guestbook";
			}
		}
		return "guestbook/delete";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(Long no, String password) {
		guestbookService.deleteMessage(no, password);
		return "redirect:/guestbook";
	}
}
