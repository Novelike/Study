package com.kjh.service;

import com.kjh.dto.MessageRequestDto;
import com.kjh.dto.MessageResponseDto;
import com.kjh.mapper.MessageMapper;
import com.kjh.vo.MessageLogVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MessageService {

    @Autowired
    private MessageMapper messageMapper;

    public List<MessageResponseDto> selectMessageLogList(MessageRequestDto requestDto) throws Exception {
        List<MessageLogVO> list = messageMapper.selectMessageLogList(requestDto);
        return MessageResponseDto.fromList(list);
    }

}
