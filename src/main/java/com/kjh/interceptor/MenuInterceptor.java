package com.kjh.interceptor;

import com.kjh.dto.ResultDTO;
import com.kjh.service.MenuService;
import com.kjh.vo.SessionVO;
import com.kjh.vo.UserMenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@Component
public class MenuInterceptor implements HandlerInterceptor {

    @Autowired
    private MenuService menuService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
            Exception {
        ResultDTO resultDTO = menuService.menuSearch();
        HashMap<String, Object> param = (HashMap<String, Object>)resultDTO.getData();
        List<UserMenuVO> menuList = (List<UserMenuVO>)param.get("userMenuList");
        request.setAttribute("menuList", menuList);

        return true;
    }

}
