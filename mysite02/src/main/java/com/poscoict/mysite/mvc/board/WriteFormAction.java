package com.poscoict.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class WriteFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getAttribute("rVo") != null) {
			BoardVo rVo = (BoardVo)request.getAttribute("rVo");
			request.setAttribute("rVo", rVo);
		}
		MvcUtil.forward("board/write", request, response);
	}

}
