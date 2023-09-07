package com.kjh.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.ZonedDateTime;

@Getter
@ToString
@NoArgsConstructor
public class InquiryVo {
    private Integer seq;
    private Integer userSeq;
    private String inquiryType;
    private String inquiryTitle;
    private String inquiryContent;
    private ZonedDateTime inquiryDate;
    private String inquiryStatus;
    private String replyContent;
    private String replyEmail;
    private String replyPhone;
    private String replyNotice;
    private ZonedDateTime replyDate;
    private Integer replyRegUserSeq;

    @Builder
    public InquiryVo(Integer seq, Integer userSeq, String inquiryType, String inquiryTitle, String inquiryContent,
                     ZonedDateTime inquiryDate, String inquiryStatus, String replyContent, String replyEmail, String replyPhone,
                     String replyNotice, ZonedDateTime replyDate, Integer replyRegUserSeq) {
        this.seq = seq;
        this.userSeq = userSeq;
        this.inquiryType = inquiryType;
        this.inquiryTitle = inquiryTitle;
        this.inquiryContent = inquiryContent;
        this.inquiryDate = inquiryDate;
        this.inquiryStatus = inquiryStatus;
        this.replyContent = replyContent;
        this.replyEmail = replyEmail;
        this.replyPhone = replyPhone;
        this.replyNotice = replyNotice;
        this.replyDate = replyDate;
        this.replyRegUserSeq = replyRegUserSeq;
    }

}