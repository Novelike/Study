package com.kjh.service;

import com.kjh.mapper.SendNumberMapper;
import com.kjh.vo.SearchVO;
import com.kjh.vo.SendNumberVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class SendNumberService {

    @Autowired
    private SendNumberMapper sendNumberMapper;

    public List<SendNumberVO> sendNumberList(int userSeq, SearchVO searchVO) throws Exception {
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userSeq", userSeq);
        int sendNumberListCnt = sendNumberMapper.selectSendNumberListCnt(paramMap);

        searchVO.setKeyfield("userSeq");
        searchVO.setKeyword(String.valueOf(userSeq));
        searchVO.setTotalCnt(sendNumberListCnt);
        searchVO.setStartRecord((searchVO.getPage() - 1) * searchVO.getSize());

        return sendNumberMapper.searchSendNumberList(searchVO);
    }

}
