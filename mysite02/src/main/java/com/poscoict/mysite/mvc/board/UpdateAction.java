package com.poscoict.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscoict.mysite.dao.BoardDao;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String title = request.getParameter("title");
		String contents = request.getParameter("content");
		String bno = request.getParameter("bno");
		String uno = request.getParameter("uno");
		if(new BoardDao().update(title, contents, bno)) {
			MvcUtil.redirect(request.getContextPath()+"/board?a=viewform&"+"bno="+bno+"&uno="+uno, request, response);
		}
		
	}

}
