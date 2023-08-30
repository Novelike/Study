package com.kjh.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequestMapping(value = "/sign")
class SignController {

    @RequestMapping(value = "/signin")
    public String goSignin(Model model, HttpServletRequest request) {
        return "/sign/signin";
    }

    @RequestMapping(value = "/signup")
    public String goSignup(Model model, HttpServletRequest request) {
        return "/sign/signup";
    }

    @RequestMapping("/signout")
    public String goSignout(Model model, HttpServletRequest request) {
        return "redirect:/user/signout";
    }

}
