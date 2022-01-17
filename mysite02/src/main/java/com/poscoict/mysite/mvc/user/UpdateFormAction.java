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

public class UpdateFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//접근제어 (Access Control)
		//접근제어 코드 들어가야한다 ( authUser null check )
		HttpSession session =request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		if(authUser == null) {
			MvcUtil.redirect(request.getContextPath()+"/user?a=loginform", request, response);
			return; // 잊지말고 return 해라 
		}
		
		UserVo vo = new UserDao().findByNo(authUser.getNo());
		//forwarding user/updateform
		request.setAttribute("userVo", vo);
		MvcUtil.forward("user/updateform", request, response);
	}

}
