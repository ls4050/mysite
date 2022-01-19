package com.poscoict.mysite.mvc.board;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscoict.mysite.dao.BoardDao;
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardDao dao = new BoardDao();
		Integer splitNum = 5; //nextpage가 없다는 것
		Integer current = 1;
		String kwd = request.getParameter("kwd");
		HttpSession session = request.getSession();
		
		
		if(request.getParameter("current") != null) {
			current = Integer.parseInt(request.getParameter("current"));
		}
		
		
		session.setAttribute("pageNum", dao.count(splitNum));
		session.setAttribute("current", current);
		
		List<BoardVo> list = new BoardDao().findAll(kwd, (current-1)*splitNum, splitNum); 
		request.setAttribute("list", list);
		
		MvcUtil.forward("board/list", request, response);
	}

}
