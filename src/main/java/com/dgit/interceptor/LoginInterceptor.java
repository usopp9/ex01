package com.dgit.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.dgit.domain.LoginDTO;

public class LoginInterceptor extends HandlerInterceptorAdapter{
	
	private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("pre Handler.........................................................");
		return super.preHandle(request, response, handler);
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		logger.info("post Handler.........................................................");
		
		LoginDTO loginDTO = (LoginDTO)modelAndView.getModel().get("loginDTO");
		if(loginDTO != null){
			logger.info("new Login Success");
			HttpSession session = request.getSession();
			session.setAttribute("login", loginDTO);
			
			Object dest = session.getAttribute("dest");
			String path = "";
			if(dest != null){
				path = (String)dest;
			}else{
				path = request.getContextPath();
			}
			response.sendRedirect(path);// 홈 화면주소
		}
	}
	
}
