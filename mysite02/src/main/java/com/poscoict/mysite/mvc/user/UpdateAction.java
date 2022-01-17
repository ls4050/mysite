package com.poscoict.mysite.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscoict.mysite.dao.UserDao;
import com.poscoict.mysite.vo.UserVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Access Control
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String gender = request.getParameter("gender");
		if(authUser == null) {
			MvcUtil.redirect(request.getContextPath()+"/user?a=loginform", request, response);
			return;
		}
		
		if(!password.isBlank()) {
			authUser.setPassword(password);			
		}
		
		if(new UserDao().update(name, password, gender, authUser.getNo())) {
			authUser.setName(name);
			authUser.setGender(gender);
			session.setAttribute("authUser", authUser);
			MvcUtil.forward("/main/index", request, response);
		}
	}

}
