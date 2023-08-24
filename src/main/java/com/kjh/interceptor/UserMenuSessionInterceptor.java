package com.kjh.interceptor;

import com.kjh.dao.MainService;
import com.kjh.vo.SessionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserMenuSessionInterceptor implements HandlerInterceptor {

	@Autowired
	private MainService mainService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {
		SessionVO svo = (SessionVO)request.getSession().getAttribute("sessionVO");
		if (svo == null || svo.getUserMenuList() == null) {
			mainService.setSession(request, null, "");
		}
		return true;
	}
}
