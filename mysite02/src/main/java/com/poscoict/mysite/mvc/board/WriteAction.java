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
		String g_no = request.getParameter("g_no");
		String o_no = request.getParameter("o_no");
		String depth = request.getParameter("depth");
		String title = request.getParameter("title");
		String contents = request.getParameter("content");
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		BoardVo vo = new BoardVo();
		if(!g_no.isBlank()) {
			//답글
			vo.setGroupNo(Integer.parseInt(g_no));
			vo.setOrderNo(Integer.parseInt(o_no));
			vo.setDepth(Integer.parseInt(depth));
		} 
		//새글
		//가입 안했을 때 컨트롤해줘야한다 
		vo.setTitle(title);
		vo.setContents(contents);
		//null point 
		vo.setUserNo(authUser.getNo());
		if(new BoardDao().insert(vo)) {
			MvcUtil.redirect(request.getContextPath()+"/board", request, response);
		}
	}
}
