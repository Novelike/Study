package com.kjh.controller;

import com.kjh.dto.DataTableResponseDto;
import com.kjh.dto.InquiryRequestDto;
import com.kjh.dto.InquiryResponseDto;
import com.kjh.dto.ResultDTO;
import com.kjh.service.MyPageService;
import com.kjh.service.SendNumberService;
import com.kjh.vo.SearchVO;
import com.kjh.vo.SendNumberVO;
import com.kjh.vo.SessionVO;
import com.kjh.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {

    @Autowired
    private final MyPageService myPageService;

    @Autowired
    private final SendNumberService sendNumberService;

    @RequestMapping("/mypage")
    public String goInquiry(HttpServletRequest request, Model model) {
        model.addAttribute("default", "menu2");
        return "/mypage/mypage";
    }

    @ResponseBody
    @PostMapping("/inquiry/select")
    public DataTableResponseDto<InquiryResponseDto> selectInquiry (@RequestBody InquiryRequestDto requestDto,
                                                                   HttpServletRequest request) throws Exception {
        extracted(requestDto, request);
        int recordsFiltered = myPageService.getInquiryCount(requestDto);
        List<InquiryResponseDto> responseDtoList = myPageService.getInquiryList(requestDto);
        return new DataTableResponseDto<>(
                requestDto.getDraw(),
                recordsFiltered,
                responseDtoList
        );
    }

    @ResponseBody
    @PostMapping("/inquiry/insert")
    public void insertInquiry (@RequestBody InquiryRequestDto requestDto, HttpServletRequest request) throws Exception {
        extracted(requestDto, request);
        myPageService.saveInquiry(requestDto);
    }

    @ResponseBody
    @PostMapping("/inquiry/update")
    public void updateInquiry (@RequestBody InquiryRequestDto requestDto, HttpServletRequest request) throws Exception {
        extracted(requestDto, request);
        myPageService.updateInquiry(requestDto);
    }

    @ResponseBody
    @RequestMapping("/inquiry/info")
    public InquiryResponseDto getInquiryInfo(@RequestParam(value = "seq") int seq, InquiryRequestDto requestDto, HttpServletRequest request) throws
            Exception {
        extracted(requestDto, request);
        requestDto.setSeq(seq);
        return myPageService.getInquiryInfo(requestDto);
    }


    @RequestMapping("/my_inquiry")
    public String getMyInquiry() { return "/mypage/content/myInquiry"; }

    @RequestMapping("/myInfo")
    public String getMyInfo() { return "/mypage/content/myInfo"; }

    @ResponseBody
    @RequestMapping("/checkPw")
    public boolean checkPw(@RequestParam(value = "userPw") String userPw, HttpServletRequest request) throws Exception {
        SessionVO sessionVO = (SessionVO) request.getSession().getAttribute("sessionVO");
        String myPw = sessionVO.getUser().getUserPw();
        return myPageService.checkPw(userPw, myPw);
    }

    @RequestMapping("/myInfoUpdate")
    public String myInfoUpdate() { return "/mypage/content/myInfoUpdate"; }

    @RequestMapping("/content3")
    public String getContent3(Model model, HttpServletRequest request, SearchVO svo) {
        SessionVO sessionVO = (SessionVO) request.getSession().getAttribute("sessionVO");
        try {
            List<SendNumberVO> sendNumberList = sendNumberService.sendNumberList(sessionVO.getUser().getSeq(), svo);
            model.addAttribute("list", sendNumberList);
            model.addAttribute("svo", svo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/mypage/content/content3";
    }

    @RequestMapping("/content4")
    public String getContent4() { return "/mypage/content/content4"; }

    private void extracted(InquiryRequestDto requestDto, HttpServletRequest request) {
        SessionVO sessionVO = (SessionVO) request.getSession().getAttribute("sessionVO");
        requestDto.setUserSeq(sessionVO.getUser().getSeq());
        log.info(String.valueOf(requestDto));
    }

}
