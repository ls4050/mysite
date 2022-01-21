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
		Integer cPage = 0;
		String tempPage = request.getParameter("page");
		if (tempPage == null || tempPage.length() == 0) {
			cPage = 1;
		}
		try {
			cPage = Integer.parseInt(tempPage);
		} catch (NumberFormatException e) {
			// TODO: handle exception
			cPage = 1;
		}
		String kwd = request.getParameter("kwd");
		Integer splitNum = 5; // 한 페이지에 들어가는 데이터 개수
		Integer start = (cPage - 1) * splitNum;
		String limit = "limit " + start + ", " + splitNum + ";";
		if (kwd != null) {
			limit = "";
		}
		List<BoardVo> list = new BoardDao().findAll(kwd, limit);

		BoardDao dao = new BoardDao();
		Integer pageLength = 5; // 한 블럭에 들어가는 페이지 개수
		Integer totalRows = dao.getTotalRows(); // 총 데이터 수
		Integer cPageBlock = (cPage % pageLength == 0) ? cPage / pageLength : (cPage / pageLength) + 1; // 현재 페이지 블록
		Integer totalPages = totalRows % splitNum == 0 ? totalRows / splitNum : (totalRows / splitNum) + 1; // 총 페이지 수
		Integer startPage = 1 + (cPageBlock - 1) * pageLength;
		Integer endPage = startPage + pageLength - 1;
		HttpSession session = request.getSession();

		if (totalPages == 0) {
			totalPages = 1;
		}
		if (cPage > totalPages) {
			cPage = 1;
		}

		request.setAttribute("totalPages", totalPages);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("cPage", cPage);

		request.setAttribute("list", list);

		MvcUtil.forward("board/list", request, response);
	}

}
