package com.kjh.controller;

import com.kjh.service.SigninService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/main")
public class MainController {

    @Autowired
    private SigninService signinService;

    @RequestMapping({"/index"})
    public String mainView(Model model, HttpServletRequest request) {
        Map<String, String> valueMap = new HashMap<String, String>();
        model.addAttribute("valueMap", valueMap);
        model.addAttribute("url", "URL은 이거다");
        return "/main/index";
    }

    @GetMapping("/sendResult")
    public String goSendResult(HttpServletRequest request, Model model) {
        return "/message/sendResult";
    }

    @GetMapping("/mypage")
    public String goMyPage(HttpServletRequest request, Model model) {
        return "redirect:/mypage/inquiry";
    }
}

