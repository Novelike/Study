package com.kjh.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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
