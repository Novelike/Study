package com.kjh.service;

import com.kjh.dto.InquiryRequestDto;
import com.kjh.dto.InquiryResponseDto;
import com.kjh.mapper.MyPageMapper;
import com.kjh.vo.InquiryVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyPageService {

    @Autowired
    private final MyPageMapper myPageMapper;

    @Autowired
    private final PasswordEncoder passwordEncoder;

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

    public void updateInquiry(InquiryRequestDto requestDto) throws Exception {
        myPageMapper.updateInquiry(requestDto);
    }

    public boolean checkPw(String userPw, String myPw) {
        log.info("userPw ==> {}",userPw);
        log.info("enc userPw ==> {}", passwordEncoder.encode(userPw));
        log.info("myPw ==> {}", myPw);
        log.info("checkPw ==> {}", passwordEncoder.matches(userPw, myPw));
        return passwordEncoder.matches(userPw, myPw);
    }

}
