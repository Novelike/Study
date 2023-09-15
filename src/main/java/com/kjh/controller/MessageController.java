package com.kjh.controller;

import com.kjh.dto.MessageRequestDto;
import com.kjh.dto.MessageResponseDto;
import com.kjh.dto.DataTableResponseDto;
import com.kjh.service.MessageService;
import com.kjh.vo.SessionVO;
import com.kjh.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @ResponseBody
    @PostMapping("/send/result")
    public DataTableResponseDto<MessageResponseDto> sendResultList(@RequestBody MessageRequestDto requestDto, HttpServletRequest request) throws Exception {
        SessionVO sessionVo = (SessionVO)request.getSession().getAttribute("sessionVO");
        UserVO userVo = sessionVo.getUser();
        requestDto.setUserSeq(userVo.getSeq());
        log.info("requestDto.getUserSeq ==> {}", requestDto.getUserSeq());
        log.info("draw ==> {}", requestDto.getDraw());
        log.info("start ==> {}", requestDto.getStart());
        log.info("length ==> {}", requestDto.getLength());
        log.info("length2 => {}", request.getAttribute("length"));
        requestDto.search();

        int recordsFiltered = messageService.selectMessageLogCount(requestDto);
        List<MessageResponseDto> responseDtoList = messageService.selectMessageLogList(requestDto);

        // DataTableResponseDto 객체를 생성하고 필요한 정보를 설정
        return new DataTableResponseDto<>(
                requestDto.getDraw(),
                recordsFiltered,
                responseDtoList
        );
    }
}
