package com.poscoict.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscoict.mysite.dao.BoardDao;
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class ViewFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bno = request.getParameter("bno");
		if(new BoardDao().hitUp(Integer.parseInt(bno))) {
			System.out.println("조회수 증가");
		}
		BoardVo vo = new BoardDao().findByBno(bno);
		request.setAttribute("vo", vo);
		MvcUtil.forward("board/view", request, response);
	}
}
