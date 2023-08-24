package com.kjh.mapper;

import com.kjh.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
@Mapper
public interface SignMapper {
    UserVO selectUser(HashMap<String, Object> param) throws  Exception;
    int insertUser(UserVO userVO) throws Exception;
    int updateUser(UserVO userVO) throws Exception;
    int insertLoginHistory(HashMap<String, Object> param) throws Exception;
}
