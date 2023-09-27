package com.kjh.controller;

import com.kjh.service.UserService;
import com.kjh.dto.ResultDTO;
import com.kjh.util.Token;
import com.kjh.vo.SessionVO;
import com.kjh.vo.UserVO;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping(value = "/user")
class UserController {

    @Autowired
    Token token;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/signin", produces = {"application/json"})
    public ResponseEntity<ResultDTO> userLogin(
            @Parameter(name = "loginId", description = "아이디", example = "test", required = true) @RequestParam(value = "loginId", required = false) String loginId,
            @Parameter(name = "loginPw", description = "비밀번호", example = "1234", required = true) @RequestParam(value = "loginPw", required = false) String loginPw,
            HttpServletRequest request
    ) throws Exception {
        ResultDTO resultDTO = new ResultDTO();
        try {
            resultDTO = userService.loginCheck(request, loginId, loginPw);

            if ("000".equals(resultDTO.getCode())) {
                UserVO userVO = (UserVO)resultDTO.getData();

                userService.setSession(request, userVO);
            }
        } catch (Exception e) {
            log.error("exception singin ==> {} ", e.getMessage());
            resultDTO.setCode("999");
            resultDTO.setMessage("오류가 발생했습니다.(" + e.toString() + ")");
        }

        return ResponseEntity.status(HttpStatus.OK).body(resultDTO);
    }

    @PostMapping("/modify")
    public ResponseEntity<ResultDTO> userModify(
            @Parameter(hidden = true) @RequestBody UserVO userVO,
            HttpServletRequest request) throws Exception {
        ResultDTO resultDTO = new ResultDTO();
        SessionVO sessionVO = (SessionVO) request.getSession().getAttribute("sessionVO");
        log.info("userVO ==> {}", userVO);
        try {
            resultDTO = userService.userModify(userVO, sessionVO);
            log.info("go try resultDTO ==> {}", resultDTO);

            if ("000".equals(resultDTO.getCode())) {
                log.info("if1");
                if (!"".equals(userVO.getUserCI()) && userVO.getUserCI() != null) {
                    log.info("if2");
                    UserVO userDTO = (UserVO)resultDTO.getData();
                    userService.setSession(request, userDTO);
                    log.info("setSession");
                }
            }
        } catch (Exception e) {
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
