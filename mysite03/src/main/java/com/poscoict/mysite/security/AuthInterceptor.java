package com.poscoict.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.poscoict.mysite.vo.UserVo;

public class AuthInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//1. handler 종류 확인 
		if(handler instanceof HandlerMethod == false) {
			//default servlet handler(assets처리)
			return true;
		}
		
		//2. casting
		//HandlerMethod 컨트롤러의 핸들러
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		
		//3. Handler Method의 @Auth 받아오기 
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		
		
		
		//4. Handler Method @Auth 가 없다면 Type에 있는지 확인(과제)
		//getMethodAnnotation을 확인함 -> auth가 없다? Class위에도 붙어있는지 확인해야한다. 
		if(auth == null) {
			auth = handlerMethod.getBeanType().getAnnotation(Auth.class);
		}

		//5. type과 method에 @Auth 가 적용이 안되어 있는 경우 ...
		if(auth == null) {
			return true;
		}
		
		
		//6. @Auth가 적용이 되어 있기 때문에 인증(Authentication) 여부 확인
		HttpSession session = request.getSession();
		if(session == null) {
			response.sendRedirect(request.getContextPath()+"/user/login");
			return false;
		}
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			response.sendRedirect(request.getContextPath()+"/user/login");
			return false;
		}
		
		
		//7. 인증 확인!!! -> controller의 handler(method) 실행
		//관리자 계정이 아닐 때 -> 
		if(!authUser.getRole().equals(auth.role())) {
			response.sendRedirect(request.getContextPath());
			return false;
		}
		return true;
	}
	
}
