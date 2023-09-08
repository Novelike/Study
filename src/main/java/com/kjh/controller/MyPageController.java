package com.kjh.controller;

import com.kjh.dto.DataTableResponseDto;
import com.kjh.dto.InquiryRequestDto;
import com.kjh.dto.InquiryResponseDto;
import com.kjh.dto.MessageResponseDto;
import com.kjh.service.MyPageService;
import com.kjh.vo.SessionVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {

    private final MyPageService myPageService;

    @RequestMapping("/inquiry")
    public String goInquiry(HttpServletRequest request, Model model) {
        return "/mypage/mypage";
    }

    @ResponseBody
    @PostMapping("/inquiry/select")
    public DataTableResponseDto<InquiryResponseDto> selectInquiry (@RequestBody InquiryRequestDto requestDto,
                                                                   HttpServletRequest request) throws Exception {
        SessionVO sessionVO = (SessionVO) request.getSession().getAttribute("sessionVO");
        requestDto.setUserSeq(sessionVO.getUser().getSeq());

        int recordsFiltered = myPageService.getInquiryCount(requestDto);
        List<InquiryResponseDto> responseDtoList = myPageService.getInquiryList(requestDto);
        return new DataTableResponseDto<>(
                requestDto.getDraw(),
                recordsFiltered,
                responseDtoList
        );
    }

    @PostMapping("/inquiry/insert")
    public DataTableResponseDto<InquiryResponseDto> insertInquiry (@RequestBody InquiryRequestDto requestDto,
                                                                   HttpServletRequest request) {
        return null;
    }

}
