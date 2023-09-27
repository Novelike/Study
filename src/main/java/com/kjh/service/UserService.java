package com.kjh.service;

import com.kjh.dto.ResultDTO;
import com.kjh.mapper.SignMapper;
import com.kjh.util.AesCoderUtil;
import com.kjh.util.StringUtil;
import com.kjh.util.Token;
import com.kjh.vo.SessionVO;
import com.kjh.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class UserService {

    @Autowired
    Token token;

    @Autowired
    SignMapper signMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
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

    @Transactional(readOnly = true)
    public int userPccCheck(String[] info, HashMap<String, Object> uvo) throws Exception {
        log.info("ci = {}", info[5]);
        uvo.put("userCi", info[5]);

        UserVO userMap = signMapper.selectUser(uvo);

        if (userMap != null) {
            uvo.put("user_id", userMap.getUserId());
            uvo.put("user_mobile", userMap.getUserMobile());
            return 10;
        }

        return 0;
    }

    public ResultDTO userModify(UserVO userVO, SessionVO sessionVO) throws Exception {
        ResultDTO resultDTO = new ResultDTO();
        int userSeq = sessionVO.getUser().getSeq();
        String userId = sessionVO.getUser().getUserId();
        String userStatus = sessionVO.getUser().getUserStatus();
        String userName = sessionVO.getUser().getUserName();

        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("seq", userSeq);
        UserVO userMap = signMapper.selectUser(paramMap);

        if (userMap == null) {
            resultDTO.setCode("006");
            resultDTO.setMessage("사용자 정보를 찾을 수 없습니다.");
        } else {
            userVO.setSeq(userMap.getSeq());
            if (!"".equals(userVO.getUserPw()) && userVO.getUserPw() != null) {
                userVO.setUserPw(passwordEncoder.encode(userVO.getUserPw()));
                log.info("setUserPw");
            }
            if (!"".equals(userVO.getUserName()) && userVO.getUserName() != null) {
                userVO.setUserName(AesCoderUtil.encAES(userVO.getUserName()));
                log.info("setUserName");
            }
            if (!"".equals(userVO.getUserMobile()) && userVO.getUserMobile() != null) {
                userVO.setUserMobile(AesCoderUtil.encAES(userVO.getUserMobile()));
                log.info("setUserMobile");
            }
            if (!"".equals(userVO.getUserEmail()) && userVO.getUserEmail() != null) {
                userVO.setUserEmail(AesCoderUtil.encAES(userVO.getUserEmail()));
                log.info("setUserEmail");
            }
            if (!"".equals(userVO.getUserCI()) && userVO.getUserCI() != null) {
                userVO.setUserCI(userVO.getUserCI());
                log.info("setUserCI");
            }
            if (!"".equals(userVO.getUserDI()) && userVO.getUserDI() != null) {
                userVO.setUserDI(userVO.getUserDI());
                log.info("setUserDI");
            }
            userVO.setModDate(ZonedDateTime.now());
            userVO.setModUserSeq(userSeq);

            int result = signMapper.updateUser(userVO);

            if (result > 0) {
                resultDTO.setCode("000");
                resultDTO.setMessage("정상적으로 수정되었습니다.");

                userVO.setUserId(userId);
                userVO.setUserStatus(userStatus);
                userVO.setUserName(userName);
                resultDTO.setData(userVO);
                log.info("resultDTO.getData ==> {}", resultDTO.getData());
            } else {
                resultDTO.setCode("005");
                resultDTO.setMessage("사용자 정보 수정에 실패 하였습니다.");
            }
        }

        return resultDTO;
    }

}
