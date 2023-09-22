package com.kjh.vo;

import lombok.Data;

@Data
public class SendNumberListVO {

	private Integer sendNumberSeq;
	private String sendNumber;
	private String regReason;
	private String certificationType;
	private String certificationStatus;
	private String certificationRejectReason;
	private String certificationDate;
	private Boolean isBaseSelect;
	private String regDate;
	private String certificationTypeName;
	private String certificationStatusName;
}
