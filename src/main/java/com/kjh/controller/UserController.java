package com.kjh.controller;

import com.kjh.service.SigninService;
import com.kjh.dto.ResultDTO;
import com.kjh.vo.UserVO;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping(value = "/user")
class UserController {
    @Autowired
    private SigninService signinService;

    @PostMapping(value = "/signin", produces = {"application/json"})
    public ResponseEntity<ResultDTO> userLogin(
            @Parameter(name = "loginId", description = "아이디", example = "test", required = true) @RequestParam(value = "loginId", required = false) String loginId,
            @Parameter(name = "loginPw", description = "비밀번호", example = "1234", required = true) @RequestParam(value = "loginPw", required = false) String loginPw,
            HttpServletRequest request
    ) throws Exception {
        ResultDTO resultDTO = new ResultDTO();
        try {
            resultDTO = signinService.loginCheck(request, loginId, loginPw);

            if ("000".equals(resultDTO.getCode())) {
                UserVO userVO = (UserVO)resultDTO.getData();

                signinService.setSession(request, userVO);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            resultDTO.setCode("999");
            resultDTO.setMessage("오류가 발생했습니다.(" + e.toString() + ")");
        }

        return ResponseEntity.status(HttpStatus.OK).body(resultDTO);
    }
    @RequestMapping(value ="/signup")
    public String signup(Model model, HttpServletRequest request) {
        return "sign/signup";
    }

    @RequestMapping(value = "/signout")
    public String signout(HttpServletRequest request, HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
