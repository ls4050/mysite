package com.poscoict.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscoict.mysite.dao.BoardDao;
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.mysite.vo.UserVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		String contents = request.getParameter("content");
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		
		//가입 안했을 때 컨트롤해줘야한다 
		BoardVo vo = new BoardVo();
		vo.setTitle(title);
		vo.setContents(contents);
		//null point 
		vo.setUserNo(authUser.getNo());
		
		
		if(new BoardDao().insert(vo)) {
			MvcUtil.redirect(request.getContextPath()+"/board", request, response);
		}
	}

}
