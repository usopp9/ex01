package com.dgit.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.dgit.domain.LoginDTO;

public class AuthInterceptor extends HandlerInterceptorAdapter{

	private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		logger.info("pre Handler.........................................................");
		
		// login안한 사용자가 제한구역에 접근시 로그인 화면으로 가도록 처리
		HttpSession session = request.getSession();
		LoginDTO dto = (LoginDTO)session.getAttribute("login");
		if(dto !=null){
			logger.info("로그인이 된 상태입니다.");
		}else{
			logger.info("로그인이 안된 상태입니다.");
			saveDest(request);
			
			response.sendRedirect(request.getContextPath() +"/user/login");
			return false;
		}
		
		return super.preHandle(request, response, handler);
	}
	
	/*로그인전에 주소를 저장하기위함*/
	private void saveDest(HttpServletRequest req){
		String uri = req.getRequestURI();// only 주소만 매개변수 x
		String query = req.getQueryString();
		
		if(query == null || query.equals("null")){
			query="";
		}else{
			query = "?" +query;
		}
		
		if(req.getMethod().equalsIgnoreCase("GET")){
			logger.info("destination : "+uri+query);
			req.getSession().setAttribute("dest", uri+query);
		}
	}
	
	
	
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		logger.info("post Handler.........................................................");
	}
}
