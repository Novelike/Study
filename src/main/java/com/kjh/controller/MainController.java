package com.kjh.controller;

import com.kjh.service.SigninService;
import com.kjh.vo.UserMenuVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
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
        List<UserMenuVO> menuList = (List<UserMenuVO>) request.getAttribute("menuList");
        model.addAttribute("valueMap", valueMap);
        model.addAttribute("menuList", menuList);
        log.info(menuList.toString());
        model.addAttribute("url", "URL은 이거다");
        return "/main/index";
    }

    @GetMapping("/send/result")
    public String goSendResult(HttpServletRequest request, Model model) { return "/message/sendResult"; }

    @GetMapping("/send/sms")
    public String goSendSms() { return "/message/sendSms"; }

    @GetMapping("/fee")
    public String goFee() { return "/main/fee"; }

    @GetMapping("/mypage")
    public String goMyPage(HttpServletRequest request, Model model) { return "redirect:/mypage/mypage"; }

    @GetMapping("/addressbook")
    public String goAddressBook() { return "/main/addressbook"; }

}