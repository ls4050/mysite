package com.poscoict.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
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
		BoardVo vo = new BoardDao().findByBno(bno);

		Cookie[] cookies = request.getCookies();
		Cookie viewCookie = null;
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (bno.equals(cookie.getName())) {
					viewCookie = cookie;
					break;
				}

			}
		}

		// 쿠키 쓰기(굽기)
		if (viewCookie == null) {
			Cookie cookie = new Cookie(bno, String.valueOf((vo.getHit() + 1)));
			cookie.setPath(request.getContextPath());
			cookie.setMaxAge(60);
			response.addCookie(cookie);
			if (new BoardDao().hitUp(Integer.parseInt(bno))) {
				System.out.println("조회수 증가");
			}

		}
		request.setAttribute("vo", vo);
		MvcUtil.forward("board/view", request, response);
	}
}
