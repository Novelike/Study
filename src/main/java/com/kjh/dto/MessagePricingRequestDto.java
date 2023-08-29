package com.kjh.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;


@Getter
@NoArgsConstructor
public class MessagePricingRequestDto {
	private ZonedDateTime startDate;
	private ZonedDateTime endDate;
	private boolean isUse;
	private Integer userSeq;
	private int count;
	private String inputYearMonth;
	private String messageType;

	@Builder
	public MessagePricingRequestDto(ZonedDateTime startDate, ZonedDateTime endDate, boolean isUse, Integer userSeq
		, int count, String inputYearMonth, String messageType) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.isUse = isUse;
		this.userSeq = userSeq;
		this.count = count;
		this.inputYearMonth = inputYearMonth;
		this.messageType = messageType;
	}

	public MessageRequestDto setMessageRequestDto() {
		return MessageRequestDto.builder()
				.startDate(this.startDate)
				.endDate(this.endDate)
				.userSeq(this.userSeq)
				.resultCode("10000")
				.build();
	}
}
