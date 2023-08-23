package com.kjh.vo;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class UserVO {
    private Integer seq;
    private String userId;
    private String userPw;
    private String userName;
    private String userMobile;
    private String userEmail;
    private String userStatus;
    private String userCI;
    private String userDI;
    private Boolean isSelfAuth;
    private ZonedDateTime selfAuthDate;
    private Boolean isMarketing;
    private ZonedDateTime lastLoginDate;
    private ZonedDateTime regDate;
    private Integer regUserSeq;
    private ZonedDateTime modDate;
    private Integer modUserSeq;
}
