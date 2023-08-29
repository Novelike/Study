package com.kjh.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class MessageLogVO {

	@Schema(description = "sequence", example = "1")
	private Integer seq;

	@Schema(description = "메세지 고유번호", example = "1605571592102")
	private String messageId;

	@Schema(description = "응답 메세지 고유번호", example = "1605571592102")
	private String messageKey;

	@Schema(description = "문자타입", example = "SMS")
	private String messageType;

	@Schema(description = "보내는사람번호", example = "01012345678")
	private String senderMobile;

	@Schema(description = "보낸시간")
	private ZonedDateTime sendDate;

	@Schema(description = "받는사람번호", example = "01012345678")
	private String receiverMobile;

	@Schema(description = "문자제목", example = "제목")
	private String messageTitle;

	@Schema(description = "문자내용")
	private String messageContent;

	@Schema(description = "첨부파일URL")
	private String attachment;

	@Schema(description = "결과코드")
	private String resultCode;

	@Schema(description = "결과상세")
	private String resultDesc;

	@Schema(description = "등록일")
	private ZonedDateTime regDate;

	@Schema(description = "등록자")
	private Integer regUserSeq;

	@Schema(description = "발송건수")
	private int count;
}
