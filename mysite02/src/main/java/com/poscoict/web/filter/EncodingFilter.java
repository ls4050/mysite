package com.poscoict.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


public class EncodingFilter implements Filter {
	public void init(FilterConfig fConfig) throws ServletException {
		
	}



	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		/* request */
		request.setCharacterEncoding("utf-8");
		
		chain.doFilter(request, response);
		
		/* response*/
		
	}

	public void destroy() {
		// TODO Auto-generated method stub
	}

}
