package com.kjh.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class SaveMessageBoxVO {

	@Schema(description = "sequence", example = "1")
	private Integer seq;

	@Schema(description = "user sequence", example = "1")
	private Integer userSeq;

	@Schema(description = "문자타입", example = "SMS")
	private String messageType;

	@Schema(description = "문자제목")
	private String messageTitle;

	@Schema(description = "문자내용")
	private String messageContent;

	@Schema(description = "문자내용길이")
	private Integer messageContentByte;

	@Schema(description = "등록일")
	private ZonedDateTime regDate;

	@Schema(description = "최종수정일")
	private String modDate;

	@Schema(description = "등록자")
	private Integer regUserSeq;
}
