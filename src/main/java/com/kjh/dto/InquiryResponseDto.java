package com.kjh.dto;

import com.kjh.vo.InquiryVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class InquiryResponseDto {
    private Integer seq;
    private Integer userSeq;
    private String inquiryType;
    private String inquiryTitle;
    private String inquiryContent;
    private ZonedDateTime inquiryDate;
    private String inquiryDateStr;
    private String inquiryStatus;
    private String replyContent;
    private String replyEmail;
    private String replyPhone;
    private String replyNotice;
    private ZonedDateTime replyDate;
    private String replyDateStr;
    private Integer replyRegUserSeq;
    private String resultCode;
    private String resultMessage;

    private int recordsTotal;
    private int recordsFiltered;

    @Builder
    public InquiryResponseDto(Integer seq, Integer userSeq, String inquiryType, String inquiryTitle, String inquiryContent,
                              ZonedDateTime inquiryDate, String inquiryDateStr, String inquiryStatus, String replyContent,
                              String replyEmail, String replyPhone, String replyNotice, ZonedDateTime replyDate, String replyDateStr,
                              Integer replyRegUserSeq, String resultCode, String resultMessage, int recordsTotal, int recordsFiltered) {
        this.seq = seq;
        this.userSeq = userSeq;
        this.inquiryType = inquiryType;
        this.inquiryTitle = inquiryTitle;
        this.inquiryContent = inquiryContent;
        this.inquiryDate = inquiryDate;
        this.inquiryDateStr = inquiryDateStr;
        this.inquiryStatus = inquiryStatus;
        this.replyContent = replyContent;
        this.replyEmail = replyEmail;
        this.replyPhone = replyPhone;
        this.replyNotice = replyNotice;
        this.replyDate = replyDate;
        this.replyDateStr = replyDateStr;
        this.replyRegUserSeq = replyRegUserSeq;
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsFiltered;
    }

    public static List<InquiryResponseDto> fromList(List<InquiryVo> list) {
        List<InquiryResponseDto> result = new ArrayList<>();
        for (InquiryVo inquiryVo : list) {
            result.add(convertDto(inquiryVo));
        }
        return result;
    }

    public static InquiryResponseDto convertDto(InquiryVo inquiryVo) {
        String replyDate = getDateStr(inquiryVo.getReplyDate());
        return InquiryResponseDto.builder()
                .seq(inquiryVo.getSeq())
                .userSeq(inquiryVo.getUserSeq())
                .inquiryType(inquiryVo.getInquiryType())
                .inquiryTitle(inquiryVo.getInquiryTitle())
                .inquiryContent(inquiryVo.getInquiryContent())
                .inquiryDate(inquiryVo.getInquiryDate())
                .inquiryDateStr(getDateStr(inquiryVo.getInquiryDate()))
                .inquiryStatus(inquiryVo.getInquiryStatus())
                .replyContent(inquiryVo.getReplyContent())
                .replyEmail(inquiryVo.getReplyEmail())
                .replyPhone(inquiryVo.getReplyPhone())
                .replyNotice(inquiryVo.getReplyNotice())
                .replyDate(inquiryVo.getReplyDate())
                .replyDateStr(getDateStr(inquiryVo.getReplyDate()))
                .replyRegUserSeq(inquiryVo.getReplyRegUserSeq())
                .build();
    }

    private static String getDateStr(ZonedDateTime dateTime) {
        return dateTime != null ? dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : "";
    }

    public static InquiryResponseDto resultDto(String resultCode, String resultMessage) {
        return InquiryResponseDto.builder()
                .resultCode(resultCode)
                .resultMessage(resultMessage)
                .build()
                ;
    }
}
