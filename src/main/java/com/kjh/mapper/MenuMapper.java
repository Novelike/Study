package com.kjh.mapper;

import com.kjh.vo.UserMenuVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface MenuMapper {

    // 사용자 메뉴 조회
    List<UserMenuVO> selectUserMenuList(HashMap<String, Object> param) throws Exception;

    // 사용자 메뉴 추가
    int insertUserMenu(HashMap<String, Object> param) throws Exception;

    // 메뉴 조회
    List<UserMenuVO> selectMenuList(HashMap<String, Object> param) throws Exception;
}
