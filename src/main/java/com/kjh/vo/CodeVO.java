package com.kjh.vo;

import lombok.Data;

//define이 필요한 코드 모음
@Data
public class CodeVO {
	public static final int RESULT_SUCCESS = 0;

	/* DB 조회 결과 */
	public static final int SELECT_SUCCESS = 0;
	public static final int SELECT_NOT_FOUND_ROW = 1000;

	public static final int UPDATE_SUCCESS = 0;
	public static final int UPDATE_NOT_FOUND_ROW = 1000;
	public static final int UPDATE_SQL_ERROR = 1001;

	public static final int INSERT_SUCCESS = 0;
	public static final int INSERT_SQL_ERROR = 1101;
	public static final int INSERT_DUPLICATE_PK = 1102;

	public static final int DELETE_SUCCESS = 0;
	public static final int DELETE_SQL_ERROR = 1101;

	public static final int SELECT_SQL_ERROR = 1201;

	/* type error */
	public static final int PARAMETER_TYPE_ERROR = 1300;
	public static final int FILE_UPLOAD_TYPE_ERROR = 1301;
	public static final int FILE_UPLOAD_SAVE_ERROR = 1302;
	public static final int FILE_UPLOAD_MAX_COUNT_ERROR = 1303;
	public static final int NOT_NUMBER_TYPE_ERROR = 1304;

	/* editor error  */
	public static final int EDITOR_TITLE_EMPTY = 1321;
	public static final int EDITOR_CONTENT_EMPTY = 1322;

	/* system error  */
	public static final int ETC_ERROR = 9999;

	/* file Type */
	public static final int FILE_TYPE_IMAGE = 8001;
	public static final int FILE_TYPE_ALL = 8002;

	/* USE FLAG  */
	public static final int USE_Y = 1;
	public static final int USE_N = 0;

	/* DB DML   */
	public static final int DB_SELECT = 1;
	public static final int DB_INSERT = 2;
	public static final int DB_UPDATE = 3;
	public static final int DB_DELETE = 4;
	public static final int DB_SKIP = 5;

	/* ERROR LEVEL */
	public static final int INFO = 1;
	public static final int ERROR = 2;
	public static final int DEBUG = 3;
	public static final int WARN = 4;

	public static final String STR_SUCCESS = "0000";
	public static final String STR_ERROR = "9999";

	public static final String NOT_FOUND_NAME = "1401";
	public static final String NOT_FOUND_MOBILE = "1402";
	public static final String NOT_FOUND_HOME = "1403";
	public static final String NOT_FOUND_COMPANY = "1404";
	public static final String NOT_FOUND_ETC = "1405";
	public static final String NOT_FOUND_GRADE = "1406";

	public static final String EXIST_ICALL = "1407";
	public static final String NOT_EXIST_ICALL = "1408";

	public static final String NUMBER_TYPE_ERROR = "1406";

	public static final String SQL_SELECT_ERR = "1500";
	public static final String SQL_UPDATE_ERR = "1501";

	//line history code_seq 
	public static final int ALL_CREATE = 501;    //관리자에서 t_user_line 테이블에 user / line 신규 등록
	public static final int USER_CREATE = 503;    //관리자에서 t_user_line 테이블에 user 신규 등록
	public static final int LINE_UPDATE = 505;    //관리자에서 t_user_line 테이블에 USER에 할당된 line_seq를 update
	public static final int LINE_DELETE = 506;    //관리자에서 t_user_line 테이블에 USER에 할당된 line_seq를 null 처리

	//t_line의 LINE_TYPE
	public static final String LINE_TYPE_SCHOOL = "SCHOOL";
	public static final String LINE_TYPE_PERSONAL = "PERSONAL";
	public static final String LINE_TYPE_EXPERIENCE = "EXPERIENCE";

	//t_fee의 fee_type 
	public static final String FEE_TYPE_BASIC = "BASIC";
	public static final String FEE_TYPE_EXTRA = "EXTRA";
	public static final String FEE_TYPE_ETC = "ETC";

	//chk이후 insert / update 지시 
	public static final String NOT_EXIST = "NOT_EXIST";
	public static final String EXIST = "EXIST";
	public static final String JOB_INSERT = "INSERT";
	public static final String JOB_UPDATE = "UPDATE";

	//SMS TYPE 
	public static final String TYPE_SMS = "SMS";
	public static final String TYPE_LMS = "LMS";
	public static final String TYPE_MMS = "MMS";

	public static final String MASTER = "master";
	public static final String SLAVE = "slave";

	public static final String CANCEL_REASON_CODE = "A0008";

	public static final String CALL_AMOUNTS = "BILLING_CALL";
	public static final String SMS_AMOUNTS = "BILLING_SMS";

	public static final String ADMIN_MENU_READ = "READ";
	public static final String ADMIN_MENU_WRITE = "WRITE";
	public static final String ADMIN_MENU_DELETE = "DELETE";

	//contract status 
	public enum contract_status {
		NOTHING, WAITING, COMPLETE, EXPIRED, value
	}

	public enum discount_type {
		RATE, AMOUNT
	}

	//공통코드 변수 
	private int code_seq;
	private String div_code;
	private String div_name;
	private String etc_name;
	private String div_level;
	private String is_use;
	private String chk_code;

}
