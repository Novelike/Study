package com.kjh.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ManageInterceptor implements HandlerInterceptor {

	private static final Logger log = LoggerFactory.getLogger(ManageInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {
		log.info(" preHandle intrceptor ! " + request.getRequestURI());
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
		ModelAndView modelAndView) throws Exception {
		log.info(" postHandle intrceptor ! " + request.getRequestURI());
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
		Exception ex) throws Exception {
		log.info(" afterHandle intrceptor ! " + request.getRequestURI());
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}
