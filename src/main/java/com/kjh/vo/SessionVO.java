package com.kjh.vo;

import lombok.Data;

import java.util.List;

@Data
public class SessionVO {
    private String token;
    private UserVO user;
    private List<UserMenuVO> userMenuList;
    private List<UserMenuVO> firstMenuList;
    private String mainRedirectUrl;
}