package com.kjh.interceptor;

import com.kjh.service.SigninService;
import com.kjh.vo.SessionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
            Exception {
        SessionVO svo = (SessionVO)request.getSession().getAttribute("sessionVO");
        if (svo == null || svo.getUser() == null) {
            request.setAttribute("msg", "로그인이 필요합니다.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/sign/signin");
            dispatcher.forward(request, response);
            return false;
        }
        return true;
    }

}
