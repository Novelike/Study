package com.kjh.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Getter
@NoArgsConstructor
public class UserPricePolicyVO {
	private Integer seq;
	private Integer userSeq;
	private String messageType;
	private Integer pricingSeq;
	private ZonedDateTime startDate;
	private ZonedDateTime endDate;
	private boolean isUse;
	private ZonedDateTime regDate;
	private Integer regUserSeq;

	@Builder
	public UserPricePolicyVO(Integer seq, Integer userSeq, String messageType, Integer pricingSeq, ZonedDateTime startDate,
		ZonedDateTime endDate, boolean isUse, ZonedDateTime regDate, Integer regUserSeq) {
		this.seq = seq;
		this.userSeq = userSeq;
		this.messageType = messageType;
		this.pricingSeq = pricingSeq;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isUse = isUse;
		this.regDate = regDate;
		this.regUserSeq = regUserSeq;
	}
}
