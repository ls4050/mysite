package com.poscoict.mysite.mvc.board;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscoict.mysite.dao.BoardDao;
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardDao dao = new BoardDao();
		Integer nextPage = -1; //nextpage가 없다는 것
		Integer startPage = 3;
		Integer prePage = 2;
		
		
		String kwd = request.getParameter("kwd");
		Map<String, Integer> m = new HashMap<String, Integer>();
		Integer count = dao.count();
		Integer pageCount = count%5 == 0 ? count/5-1 : count/5;
		
		Integer current = 0;
		if(request.getParameter("current")!=null) {
			current = Integer.parseInt(request.getParameter("current"));
		}
		
		
		m.put("count", pageCount);
		m.put("current", current);
		request.setAttribute("m", m);
		
		List<BoardVo> list = new BoardDao().findAll(kwd, current); 
		request.setAttribute("list", list);
		
		MvcUtil.forward("board/list", request, response);
	}

}
