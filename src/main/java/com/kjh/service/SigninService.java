package com.kjh.service;

import com.kjh.dto.ResultDTO;
import com.kjh.mapper.SignMapper;
import com.kjh.util.AesCoderUtil;
import com.kjh.util.StringUtil;
import com.kjh.vo.SessionVO;
import com.kjh.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;
import java.util.HashMap;
@Slf4j
@Service
public class SigninService {
    @Autowired
    SignMapper signMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResultDTO loginCheck(HttpServletRequest request, String loginId, String loginPw) throws Exception {
        ResultDTO resultDTO = new ResultDTO();

        if ("".equals(loginId) || loginId == null) {
            resultDTO.setCode("001");
            resultDTO.setMessage("아이디가 넘어오지 않았습니다.");
            return resultDTO;
        }

        if ("".equals(loginPw) || loginPw == null) {
            resultDTO.setCode("002");
            resultDTO.setMessage("비밀번호가 넘어오지 않았습니다.");
            return resultDTO;
        }

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", loginId);
        UserVO signMap = signMapper.selectUser(paramMap);

        if (signMap == null) {
            resultDTO.setCode("003");
            resultDTO.setMessage("아이디 또는 비밀번호를 다시 확인하세요.");
        } else {
            if (passwordEncoder.matches(loginPw, signMap.getUserPw())) {
                if ("B0101".equals(signMap.getUserStatus())) {
                    if (Boolean.TRUE.equals(signMap.getIsSelfAuth())) {
                        UserVO updateUserVO = new UserVO();
                        updateUserVO.setLastLoginDate(ZonedDateTime.now());
                        updateUserVO.setModDate(ZonedDateTime.now());
                        updateUserVO.setModUserSeq(signMap.getSeq());
                        updateUserVO.setSeq(signMap.getSeq());
                        signMapper.updateUser(updateUserVO);

                        HashMap<String, Object> loginHistoryMap = new HashMap<>();
                        loginHistoryMap.put("userSeq", signMap.getSeq());
                        loginHistoryMap.put("userAgent", request.getHeader("User-Agent"));
                        loginHistoryMap.put("loginIp", StringUtil.getIp(request));
                        loginHistoryMap.put("loginDate", ZonedDateTime.now());
                        signMapper.insertLoginHistory(loginHistoryMap);

                        resultDTO.setCode("000");
                        resultDTO.setMessage("로그인 성공.");

                        signMap.setUserName(AesCoderUtil.decAES(signMap.getUserName()));
                        signMap.setUserEmail(AesCoderUtil.decAES(signMap.getUserEmail()));
                        signMap.setUserMobile(AesCoderUtil.decAES(signMap.getUserMobile()));
                        resultDTO.setData(signMap);
                    } else {
                        resultDTO.setCode("106");
                        resultDTO.setMessage("본인 인증을 하지 않았습니다.");
                    }
                } else if ("B0102".equals(signMap.getUserStatus())) {
                    resultDTO.setCode("104");
                    resultDTO.setMessage("휴면 회원 입니다.");
                } else if ("B0103".equals(signMap.getUserStatus())) {
                    resultDTO.setCode("105");
                    resultDTO.setMessage("탈퇴 회원 입니다.");
                }
            } else {
                resultDTO.setCode("103");
                resultDTO.setMessage("비밀번호가 일치하지 않습니다.");
            }
        }

        return resultDTO;
    }

    public void setSession(HttpServletRequest request, UserVO userVO) {
        SessionVO sessionVO = new SessionVO();
        sessionVO.setUser(userVO);
        request.getSession().setAttribute("sessionVO", sessionVO);
        log.debug("sessionVO ==> {}", sessionVO);
        log.debug("sessionVO.getUser() ==> {}", sessionVO.getUser());
    }

    public boolean isLogin(HttpServletRequest request) {
        SessionVO sessionVO = (SessionVO) request.getSession().getAttribute("sessionVO");
        return sessionVO == null;
    }

}
