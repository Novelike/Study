package com.kjh.controller;

import com.kjh.dto.DataTableResponseDto;
import com.kjh.dto.InquiryRequestDto;
import com.kjh.dto.InquiryResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {

    public DataTableResponseDto<InquiryResponseDto> sendResultList (@RequestBody InquiryRequestDto requestDto, HttpServletRequest request) {
        return null;
    }
    
}
