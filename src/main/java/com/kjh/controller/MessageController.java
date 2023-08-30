package com.kjh.controller;

import com.kjh.dto.MessageRequestDto;
import com.kjh.dto.MessageResponseDto;
import com.kjh.service.MessageService;
import com.kjh.vo.SessionVO;
import com.kjh.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @ResponseBody
    @PostMapping("/sendResult")
    public List<MessageResponseDto> sendResultList(@RequestBody MessageRequestDto requestDto, HttpServletRequest request) throws Exception {
        SessionVO sessionVo = (SessionVO)request.getSession().getAttribute("sessionVO");
        UserVO userVo = sessionVo.getUser();
        requestDto.setUserSeq(userVo.getSeq());
        log.info("requestDto.getUserSeq ==> {}", requestDto.getUserSeq());
        requestDto.search();
        return messageService.selectMessageLogList(requestDto);
    }

}
