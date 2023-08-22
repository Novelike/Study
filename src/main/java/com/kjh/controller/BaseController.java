package com.kjh.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = {"/"})
public class BaseController {

    @RequestMapping(value = {"/pre-order"})
    public String preOrderView() {
        return "reservation/index";
    }

    @RequestMapping(value = {"", "/"})
    public String mainView() {
        return "redirect:/main/";
    }

    @RequestMapping(value = {"/redirectUrl"})
    public String redirectUrl(Model model, String m) {

        model.addAttribute("m", m);
        return "redirectUrl";
    }

    @RequestMapping(value = {"/permissionError"})
    public String permissionError(Model model) {
        return "permissionError";
    }

    @RequestMapping(value = {"/logout"})
    public String logout(HttpServletRequest request, HttpSession session) {

        session.invalidate();
        return "redirect:/redirectUrl?m=logout";
    }
}
