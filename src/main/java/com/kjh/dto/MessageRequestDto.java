package com.kjh.dto;

import com.kjh.util.AesCoderUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
public class MessageRequestDto {
	private String messageTitle;
	private String messageContent;
	private String messageType;
	private String receiverMobile;
	private String resultCode;
	private String sendDate;
	private Integer userSeq;
	private String inputStartDate;
	private String inputEndDate;
	private ZonedDateTime startDate;
	private ZonedDateTime endDate;
	private String resultStatus;

	private String[] messageTypeArray = null;

	private int page = 1;
	private int size = 10;
	private int totalCnt;
	private int startRecord;

	private int draw;
	private int start;
	private int length;

	private int recordsTotal;
	private int recordsFiltered;

	@Builder
	public MessageRequestDto(String messageTitle, String messageContent, String messageType, String receiverMobile
		, String resultCode, String sendDate, Integer userSeq, String inputStartDate, String inputEndDate
		, ZonedDateTime startDate, ZonedDateTime endDate, String[] messageTypeArray, int page, int size
		, int totalCnt, int startRecord, int draw, int start, int length) {
		this.messageTitle = messageTitle;
		this.messageContent = messageContent;
		this.messageType = messageType;
		this.receiverMobile = receiverMobile;
		this.resultCode = resultCode;
		this.sendDate = sendDate;
		this.userSeq = userSeq;
		this.messageTypeArray = messageTypeArray;
		this.inputStartDate = inputStartDate;
		this.inputEndDate = inputEndDate;
		this.startDate = startDate;
		this.endDate = endDate;
		this.page = page;
		this.size = size;
		this.totalCnt = totalCnt;
		this.startRecord = startRecord;
		this.draw = draw;
		this.start = start;
		this.length = length;
	}

	public void search() {
		if (StringUtils.isNotBlank(this.messageType)) {
			this.messageTypeArray = this.messageType.split(",");
		}
		if (StringUtils.isNotBlank(this.receiverMobile)) {
			this.receiverMobile = convertStringEncAes(this.receiverMobile);
		}
		if (StringUtils.isNotBlank(this.inputStartDate) && StringUtils.isNotBlank(this.inputEndDate)) {
			this.startDate = convertStringToZonedDateTime(this.inputStartDate + " 00:00:00");
			this.endDate = convertStringToZonedDateTime(this.inputEndDate + " 23:59:59");
		}
	}
	private ZonedDateTime convertStringToZonedDateTime(String dateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return ZonedDateTime.parse(dateString, formatter.withZone(ZoneId.of("Asia/Seoul")));
	}
	private String convertStringEncAes(String text) {
		String result;
		try {
			return AesCoderUtil.encAES(text);
		} catch (Exception e) {
			e.printStackTrace();
			result = "";
		}
		return result;
	}

}
