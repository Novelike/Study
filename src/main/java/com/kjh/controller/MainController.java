package com.kjh.controller;

import com.kjh.dao.MainService;
import com.kjh.dao.SigninDAO;
import com.kjh.dto.ResultDTO;
import com.kjh.util.Token;
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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping(value = "/main")
public class MainController {
    @RequestMapping(value = {"/index", "/"})
    public String mainView(Model model, HttpServletRequest request) {
        Map<String, String> valueMap = new HashMap<String, String>();
        model.addAttribute("valueMap", valueMap);
        model.addAttribute("url", "URL은 이거다");
        return "main/index";
    }

    @RequestMapping(value = "/signin")
    public String goSignin(Model model, HttpServletRequest request) {
        return "redirect:/sign/signin";
    }

    @RequestMapping(value = "/signup")
    public String goSignup(Model model, HttpServletRequest request) {
        return "redirect:/sign/signup";
    }
}
@Slf4j
@Controller
@RequestMapping(value = "/sign")
class SignController {
    @RequestMapping(value = "/signin")
    public String goSignin(Model model, HttpServletRequest request) {
        return "sign/signin";
    }
    @RequestMapping(value = "/signup")
    public String goSignup(Model model, HttpServletRequest request) {
        return "sign/signup";
    }
}
@Slf4j
@Controller
@RequestMapping(value = "/user")
class UserController {
    @Autowired
    private SigninDAO signinDAO;

    @Autowired
    MainService mainService;

    @Autowired
    private Token token;
    @PostMapping(value = "/signin", produces = {"application/json"})
    public ResponseEntity<ResultDTO> userLogin(
            @Parameter(name = "loginId", description = "아이디", example = "test", required = true) @RequestParam(value = "loginId", required = false) String loginId,
            @Parameter(name = "loginPw", description = "비밀번호", example = "1234", required = true) @RequestParam(value = "loginPw", required = false) String loginPw,
            HttpServletRequest request
    ) throws Exception {
        ResultDTO resultDTO = new ResultDTO();

        try {
            resultDTO = signinDAO.loginCheck(request, loginId, loginPw);

            if ("000".equals(resultDTO.getCode())) {
                UserVO userDTO = (UserVO)resultDTO.getData();

                Map<String, Object> tokenMap = new LinkedHashMap<>();
                tokenMap.put("userSeq", userDTO.getSeq());
                tokenMap.put("userId", userDTO.getUserId());
                tokenMap.put("userName", userDTO.getUserName());
                tokenMap.put("userStatus", userDTO.getUserStatus());

                mainService.setSession(request, userDTO, token.tokenEncode(tokenMap));
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
}