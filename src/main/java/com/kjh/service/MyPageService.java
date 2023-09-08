package com.kjh.service;

import com.kjh.dto.InquiryRequestDto;
import com.kjh.dto.InquiryResponseDto;
import com.kjh.mapper.MyPageMapper;
import com.kjh.vo.InquiryVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MyPageMapper myPageMapper;

    public int getInquiryCount(InquiryRequestDto requestDto) throws Exception {
        return myPageMapper.getInquiryCount(requestDto);
    }

    public List<InquiryResponseDto> getInquiryList(InquiryRequestDto requestDto) throws Exception {
        List<InquiryVo> list = myPageMapper.getInquiryList(requestDto);
        return InquiryResponseDto.fromList(list);
    }

    @Transactional
    public void saveInquiry(InquiryRequestDto requestDto) throws Exception {
        requestDto.setReplyNotice("Y");
        requestDto.setInquiryStatus("B0501");
        myPageMapper.createInquiry(requestDto);
    }

    public InquiryResponseDto getInquiryInfo(InquiryRequestDto requestDto) throws Exception {
        InquiryVo inquiryVo = myPageMapper.getInquiryInfo(requestDto);
        return InquiryResponseDto.convertDto(inquiryVo);
    }

}
