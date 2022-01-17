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

public class LoginAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		//인증받은 유저
		UserVo authUser = new UserDao().findByEmailAndPassword(email, password);
		
		if(authUser == null) {
			/* 이메일 또는 비밀번호가 틀림 */
			request.setAttribute("result", "fail");
			request.setAttribute("email", email);
			MvcUtil.forward("user/loginform", request, response);
			return; //return 해서 끝내줘야한다 아니면 아래 코드 실행된다.
		}
		
		//인증 처리(Session 처리)
		// 1, 2 번 처리해줌 
		HttpSession session = request.getSession(true);
		session.setAttribute("authUser", authUser);
		
		MvcUtil.redirect(request.getContextPath(), request, response);
	}

}
