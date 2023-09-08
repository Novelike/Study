package com.kjh.mapper;

import com.kjh.dto.InquiryRequestDto;
import com.kjh.vo.InquiryVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyPageMapper {
    Integer getInquiryCount(InquiryRequestDto inquiryRequestDto) throws Exception;

    List<InquiryVo> getInquiryList(InquiryRequestDto inquiryRequestDto) throws Exception;

    void createInquiry(InquiryRequestDto requestDto) throws Exception;

    InquiryVo getInquiryInfo(InquiryRequestDto requestDto) throws Exception;
}
