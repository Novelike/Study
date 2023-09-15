package com.kjh.service;

import com.kjh.dto.ResultDTO;
import com.kjh.mapper.MenuMapper;
import com.kjh.vo.UserMenuVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;

@Service
public class MenuService {

    private static final Logger log = LoggerFactory.getLogger(MenuService.class);

    @Autowired
    private final MenuMapper menuMapper;

    public MenuService(MenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

    public ResultDTO menuSearch() throws Exception {
        ResultDTO resultDTO = new ResultDTO();

        List<UserMenuVO> userMenuList = null;
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("isUse", true);
        userMenuList = menuMapper.selectMenuList(paramMap);

        if (userMenuList == null || userMenuList.size() == 0) {
            resultDTO.setCode("100");
            resultDTO.setMessage("메뉴 정보가 없습니다.");
        } else {
            resultDTO.setCode("000");
            resultDTO.setMessage("메뉴 조회 완료");

            for (UserMenuVO userMenuVO : userMenuList) {
                String menuUrl = userMenuVO.getMenuUrl();
                String className = "";
                if (menuUrl == null || "".equals(menuUrl.trim())) {
                    className = "blank";
                } else {
                    className = menuUrl.substring(menuUrl.lastIndexOf("/") + 1);
                }
                userMenuVO.setClassName(className);
            }

            HashMap<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("userMenuList", userMenuList);
            resultDTO.setData(resultMap);
        }
        log.info(String.valueOf(resultDTO));
        return resultDTO;
    }

    public Integer insertUserMenu(int userSeq, int regUserSeq) throws Exception {
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userSeq", userSeq);
        paramMap.put("regDate", ZonedDateTime.now());
        paramMap.put("regUserSeq", regUserSeq);
        int rlst = menuMapper.insertUserMenu(paramMap);

        return rlst;
    }

    public ResultDTO subMenuSearch(int userSeq, int mainCode) throws Exception {
        ResultDTO resultDTO = new ResultDTO();

        List<UserMenuVO> userMenuList = null;

        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userSeq", userSeq);
        paramMap.put("mainCode", mainCode);
        paramMap.put("isUse", true);
        userMenuList = menuMapper.selectUserMenuList(paramMap);

        if (userMenuList == null || userMenuList.size() == 0) {
            resultDTO.setCode("100");
            resultDTO.setMessage("메뉴 정보가 없습니다.");
        } else {
            resultDTO.setCode("000");
            resultDTO.setMessage("메뉴 조회 완료");

            UserMenuVO delMenu = null;
            for (UserMenuVO userMenuVO : userMenuList) {
                if (userMenuVO.getSub1Code() == 0 && userMenuVO.getSub2Code() == 0) {
                    delMenu = userMenuVO;
                    continue;
                }
                String menuUrl = userMenuVO.getMenuUrl();
                String className = "";
                if (menuUrl == null || "".equals(menuUrl.trim())) {
                    className = "blank";
                } else {
                    className = menuUrl.substring(menuUrl.lastIndexOf("/") + 1);
                }
                userMenuVO.setClassName(className);
            }
            userMenuList.remove(delMenu);

            resultDTO.setData(userMenuList);
        }

        return resultDTO;
    }
}
