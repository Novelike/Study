package com.kjh.vo;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class UserMenuVO {

    private Integer seq;
    private Integer userSeq;
    private Integer menuSeq;
    private Integer mainCode;
    private Integer sub1Code;
    private Integer sub2Code;
    private String menuName;
    private String menuUrl;
    private String className;
    private Integer menuSort;
    private ZonedDateTime regDate;
    private Integer regUserSeq;
}
