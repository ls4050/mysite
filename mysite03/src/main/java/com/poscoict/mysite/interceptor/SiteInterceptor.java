package com.poscoict.mysite.interceptor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.poscoict.mysite.service.SiteService;
import com.poscoict.mysite.vo.SiteVo;

public class SiteInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	private SiteService siteService;

	@Autowired
	private ServletContext servletContext;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		SiteVo siteVo = (SiteVo)servletContext.getAttribute("site");
		if(siteVo == null) {
			siteVo = siteService.getContents();
		}
		servletContext.setAttribute("site", siteVo);
		return true;
	}
	
	
}