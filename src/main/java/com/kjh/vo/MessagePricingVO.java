package com.kjh.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@NoArgsConstructor
public class MessagePricingVO {
	private Integer seq;
	private String messageType;
	private BigDecimal price;
	private ZonedDateTime startDate;
	private ZonedDateTime endDate;
	private boolean isUse;
	private ZonedDateTime regDate;
	private Integer regUserSeq;
	private int count;

	@Builder
	public MessagePricingVO(Integer seq, String messageType, BigDecimal price, ZonedDateTime startDate,
		ZonedDateTime endDate, boolean isUse, ZonedDateTime regDate, Integer regUserSeq, int count) {
		this.seq = seq;
		this.messageType = messageType;
		this.price = price;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isUse = isUse;
		this.regDate = regDate;
		this.regUserSeq = regUserSeq;
		this.count = count;
	}
}
