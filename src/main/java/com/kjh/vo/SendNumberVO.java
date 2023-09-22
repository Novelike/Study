package com.kjh.vo;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class SendNumberVO {

    private Integer seq;
    private Integer userSeq;
    private String sendNumber;
    private String regReason;
    private String certificationType;
    private String certificationStatus;
    private String certificationRejectReason;
    private ZonedDateTime certificationDate;
    private Boolean isBaseSelect;
    private ZonedDateTime regDate;
    private Integer regUserSeq;
    private ZonedDateTime modDate;
    private Integer modUserSeq;
}