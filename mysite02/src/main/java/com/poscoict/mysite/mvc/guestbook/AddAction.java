package com.poscoict.mysite.mvc.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscoict.mysite.dao.GuestbookDao;
import com.poscoict.mysite.vo.GuestbookVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class AddAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		String password = request.getParameter("pass");
		String message = request.getParameter("message");
		GuestbookVo vo = new GuestbookVo();
		GuestbookDao dao = new GuestbookDao();

		vo.setName(name);
		vo.setPassword(password);
		vo.setMessage(message);

		if (dao.insert(vo)) {
			MvcUtil.redirect("/mysite02/guestbook", request, response);
		}

	}

}
