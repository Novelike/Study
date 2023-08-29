package com.kjh.vo;

import lombok.Data;

@Data
public class SearchVO {
	private int page = 1;
	private int size = 10;

	private int totalCnt;        // 전체 조회 건 수
	private int startRecord;    // 테이블 전체의 시작 위치

	private String keyfield;
	private String keyword;

	private String schMode1;
	private String schMode2;
	private String searchValue1;

	private String selectValue1;
	private String selectValue2;
	private String selectValue3;

	private String schDateSt1;
	private String schDateEd1;

	private String schDateSt2;
	private String schDateEd2;

	private String is_end;
}
