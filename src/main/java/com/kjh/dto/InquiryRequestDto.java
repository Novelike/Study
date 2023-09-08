package com.kjh.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class InquiryRequestDto {

    private Integer seq;
    private Integer userSeq;
    private String inquiryType;
    private String inquiryStatus;
    private String inquiryTitle;
    private String inquiryContent;
    private String replyEmail;
    private String replyPhone;
    private String replyNotice;

    private int draw;
    private int start;
    private int length;

    private int recordsTotal;
    private int recordsFiltered;

    @Builder
    public InquiryRequestDto(Integer seq, Integer userSeq, String inquiryType, String inquiryTitle
            , String inquiryContent, String inquiryStatus, String replyEmail, String replyPhone, String replyNotice) {
        this.seq = seq;
        this.userSeq = userSeq;
        this.inquiryType = inquiryType;
        this.inquiryTitle = inquiryTitle;
        this.inquiryContent = inquiryContent;
        this.inquiryStatus = inquiryStatus;
        this.replyEmail = replyEmail;
        this.replyPhone = replyPhone;
        this.replyNotice = replyNotice;
    }

}
