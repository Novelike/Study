package com.kjh.interceptor;

import com.kjh.vo.SessionVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
            Exception {
        SessionVO svo = (SessionVO)request.getSession().getAttribute("sessionVO");
        if (svo == null || svo.getUser() == null) {
            boolean ajax = Objects.equals(request.getHeader("ajax"), "true");
            request.setAttribute("msg", "로그인이 필요합니다.");
            if(ajax) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/sign/signin");
            dispatcher.forward(request, response);
            return false;
        }
        return true;
    }

}
