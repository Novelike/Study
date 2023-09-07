package com.kjh.dto;

import com.kjh.vo.MessageLogVO;
import com.kjh.util.AesCoderUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MessageResponseDto {
	private String messageTitle;
	private String messageContent;
	private String messageType;
	private String senderMobile;
	private String receiverMobile;
	private String resultCode;
	private String resultMessage;
	private ZonedDateTime sendDate;
	private String sendDateStr;
	private int recordsTotal;
	private int recordsFiltered;

	@Builder
	public MessageResponseDto(String messageTitle, String messageContent, String messageType
		, String senderMobile, String receiverMobile, String resultCode, String resultMessage
		, ZonedDateTime sendDate, String sendDateStr, int recordsTotal, int recordsFiltered) {
		this.messageTitle = messageTitle;
		this.messageContent = messageContent;
		this.messageType = messageType;
		this.senderMobile = senderMobile;
		this.receiverMobile = receiverMobile;
		this.resultCode = resultCode;
		this.resultMessage = resultMessage;
		this.sendDate = sendDate;
		this.sendDateStr = sendDateStr;
		this.recordsTotal = recordsTotal;
		this.recordsFiltered = recordsFiltered;
	}

	public static List<MessageResponseDto> fromList(List<MessageLogVO> list) {
		List<MessageResponseDto> result = new ArrayList<>();
		for (MessageLogVO messageLogVO : list) {
			result.add(convertDto(messageLogVO));
		}
		return result;
	}

	private static MessageResponseDto convertDto(MessageLogVO messageLogVO) {
		return MessageResponseDto.builder()
			.messageTitle(convertStringDesAes(messageLogVO.getMessageTitle()))
			.messageContent(convertStringDesAes(messageLogVO.getMessageContent()))
			.messageType(messageLogVO.getMessageType())
			.senderMobile(convertStringDesAes(messageLogVO.getSenderMobile()))
			.receiverMobile(convertStringDesAes(messageLogVO.getReceiverMobile()))
			.resultCode(messageLogVO.getResultCode())
			.resultMessage(messageLogVO.getResultDesc())
			.sendDate(messageLogVO.getSendDate())
			.sendDateStr(messageLogVO.getSendDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
			.build();
	}

	private static String convertStringDesAes(String text) {
		String result;
		try {
			return AesCoderUtil.decAES(text);
		} catch (Exception e) {
			result = "";
		}
		return result;
	}


}
