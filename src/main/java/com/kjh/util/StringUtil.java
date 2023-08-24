package com.kjh.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.TextUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class StringUtil {

	//지금 날자를 구함 
	public static String getTimeNow(String Type) {
		Date now_date = new Date();
		LocalTime now_time = LocalTime.now();
		String ret = new String();

		if (Type.equals("yyyy-MM-dd HH:mm:ss")) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String formatedDate = simpleDateFormat.format(now_date);

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
			String formatedNow = now_time.format(formatter);

			ret = formatedDate + " " + formatedNow;
		} else {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String formatedDate = simpleDateFormat.format(now_date);

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
			String formatedNow = now_time.format(formatter);

			ret = "[" + formatedDate + " " + formatedNow + "]";
		}

		return ret;
	}

	//배열 안에 비어있는 원소를 제외한 결과물을 파싱한다.처리한다. 
	public static List<String> RegexToList(String Temp, String Regex) {
		String tempArray[] = Temp.split(Regex);
		List<String> seqArray = new ArrayList<String>(Arrays.asList(tempArray));

		//비어 있는 원소를 처리 seqArray
		for (int i = 0; i < seqArray.size(); i++) {
			if (seqArray.get(i) == null || seqArray.get(i).trim().isEmpty()) {
				seqArray.remove(i);
			}
		}

		return seqArray;
	}

	//전화번호 앞에 0이 없으면 0을 붙임 
	public static String phoneNumberZeroAdd(String num) {
		String formatNum = "";
		if (TextUtils.isEmpty(num))
			return formatNum;

		if (num.charAt(0) != '0')
			formatNum = "0" + num;
		else
			formatNum = num;

		return formatNum;
	}

	//문자열이 빈 값인지 확인 
	// true : null, false : 빈 값이 아님
	public static boolean isEmpty(String temp) {

		if (temp == null)
			return true;

		if (temp.trim().isEmpty())
			return true;

		if (temp.trim().equals(""))
			return true;

		return false;
	}

	//전화번호 하이픈 추가
	public static String phoneNumberHyphenAdd(String num, String mask) {

		String formatNum = "";
		if (TextUtils.isEmpty(num))
			return formatNum;
		num = num.replaceAll("-", "");

		if (num.length() == 11) {
			if (mask.equals("Y")) {
				formatNum = num.replaceAll("(\\d{3})(\\d{3,4})(\\d{4})", "$1-****-$3");
			} else {
				formatNum = num.replaceAll("(\\d{3})(\\d{3,4})(\\d{4})", "$1-$2-$3");
			}
		} else if (num.length() == 8) {
			formatNum = num.replaceAll("(\\d{4})(\\d{4})", "$1-$2");
		} else {
			if (num.indexOf("02") == 0) {
				if (mask.equals("Y")) {
					formatNum = num.replaceAll("(\\d{2})(\\d{3,4})(\\d{4})", "$1-****-$3");
				} else {
					formatNum = num.replaceAll("(\\d{2})(\\d{3,4})(\\d{4})", "$1-$2-$3");
				}
			} else {
				if (mask.equals("Y")) {
					formatNum = num.replaceAll("(\\d{3})(\\d{3,4})(\\d{4})", "$1-****-$3");
				} else {
					formatNum = num.replaceAll("(\\d{3})(\\d{3,4})(\\d{4})", "$1-$2-$3");
				}
			}
		}
		return formatNum;
	}

	//문자열이 int 형인지 확인 
	public static boolean IsIngeter(String temp) {
		if (temp == null)
			return false;

		if (temp.trim().isEmpty() || temp.trim().equals("")) {
			return false;
		}

		for (int i = 0; i < temp.length(); i++) {
			if (Character.isDigit(temp.charAt(i)) == false) {
				return false;
			}
		}

		return true;
	}

	//문자열이 int 형인지 확인 - int 형이 아니면 null 값을 리턴함  
	public static Integer IsIngeterToNull(String temp) {
		if (temp == null)
			return null;

		if (temp.trim().isEmpty() || temp.trim().equals("")) {
			return null;
		}

		for (int i = 0; i < temp.length(); i++) {
			if (Character.isDigit(temp.charAt(i)) == false) {
				return null;
			}
		}

		return Integer.parseInt(temp);
	}

	//문자열이 int 형인지 확인 - int 형이 아니면 0 값을 리턴함  
	public static Integer IsIngeterToZero(String temp) {
		if (temp == null)
			return 0;

		if (temp.trim().isEmpty() || temp.trim().equals("")) {
			return 0;
		}

		for (int i = 0; i < temp.length(); i++) {
			if (Character.isDigit(temp.charAt(i)) == false) {
				return null;
			}
		}

		return Integer.parseInt(temp);
	}

	// 빈 값이면 null 리턴 - 일부 DB 상에 '' 값이 아닌 null을 별도로 세팅해야 하는 경우 
	public static String getNullObject(String tmp) {
		if (tmp == null)
			return null;

		if (tmp.trim().isEmpty() || tmp.trim().equals("")) {
			return null;
		}

		return tmp;
	}

	//파일 확장자에 따른 file type 리턴 
	public String getFileType(String fileType) {

		if (fileType.toLowerCase().equals("jpg") || fileType.toLowerCase().equals("tiff")
			|| fileType.toLowerCase().equals("pjp") || fileType.toLowerCase().equals("jfif")
			|| fileType.toLowerCase().equals("bmp") || fileType.toLowerCase().equals("gif")
			|| fileType.toLowerCase().equals("svg") || fileType.toLowerCase().equals("png")
			|| fileType.toLowerCase().equals("xbm") || fileType.toLowerCase().equals("dib")
			|| fileType.toLowerCase().equals("jxl") || fileType.toLowerCase().equals("jpeg")
			|| fileType.toLowerCase().equals("svgz") || fileType.toLowerCase().equals("webp")
			|| fileType.toLowerCase().equals("ico") || fileType.toLowerCase().equals("tif")
			|| fileType.toLowerCase().equals("pjpeg") || fileType.toLowerCase().equals("avif")
		)
			return "image";

		if (fileType.toLowerCase().equals("xlsx") || fileType.toLowerCase().equals("xls"))
			return "excel";

		return fileType.toLowerCase().toString();
	}

	// LPAD
	public static String setLPad(String strContext, int iLen, String strChar) {
		String strResult = "";
		StringBuilder sbAddChar = new StringBuilder();

		for (int i = strContext.length(); i < iLen; i++) {  // iLen길이 만큼 strChar문자로 채운다.
			sbAddChar.append(strChar);
		}

		strResult = sbAddChar + strContext;  // LPAD이므로, 채울문자열 + 원래문자열로 Concate한다.

		return strResult;
	}

	public static String getIp(HttpServletRequest request) {

		String ip = request.getHeader("X-Forwarded-For");

		log.info(">>>> X-FORWARDED-FOR : " + ip);

		if (ip == null) {
			ip = request.getHeader("Proxy-Client-IP");
			log.info(">>>> Proxy-Client-IP : " + ip);
		}
		if (ip == null) {
			ip = request.getHeader("WL-Proxy-Client-IP"); // 웹로직
			log.info(">>>> WL-Proxy-Client-IP : " + ip);
		}
		if (ip == null) {
			ip = request.getHeader("HTTP_CLIENT_IP");
			log.info(">>>> HTTP_CLIENT_IP : " + ip);
		}
		if (ip == null) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			log.info(">>>> HTTP_X_FORWARDED_FOR : " + ip);
		}
		if (ip == null) {
			ip = request.getRemoteAddr();
		}

		log.info(">>>> Result : IP Address : " + ip);

		return ip;

	}

	public static boolean byteCheck(String txt, int standardByte) {

		// 바이트 체크 (영문 1, 한글 2, 특문 1)
		int en = 0;
		int ko = 0;
		int etc = 0;

		char[] txtChar = txt.toCharArray();
		for (int j = 0; j < txtChar.length; j++) {
			if (txtChar[j] >= 'A' && txtChar[j] <= 'z') {
				en++;
			} else if (txtChar[j] >= '\uAC00' && txtChar[j] <= '\uD7A3') {
				ko++;
				ko++;
			} else {
				etc++;
			}
		}

		int txtByte = en + ko + etc;

		log.info("txtByte : {}", txtByte);

		if (txtByte > standardByte) {
			return false;
		} else {
			return true;
		}

	}

	public static int getByte(String txt) {

		// 바이트 체크 (영문 1, 한글 2, 특문 1)
		int en = 0;
		int ko = 0;
		int etc = 0;

		char[] txtChar = txt.toCharArray();
		for (int j = 0; j < txtChar.length; j++) {
			if (txtChar[j] >= 'A' && txtChar[j] <= 'z') {
				en++;
			} else if (txtChar[j] >= '\uAC00' && txtChar[j] <= '\uD7A3') {
				ko++;
				ko++;
			} else {
				etc++;
			}
		}

		int txtByte = en + ko + etc;

		log.info("txtByte : {}", txtByte);

		return txtByte;
	}

	public static boolean isValidPhone(String phone) {
		boolean result = false;
		String regex = "^^\\d{2,3}\\d{3,4}\\d{4}$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(phone.trim());
		if (m.matches()) {
			result = true;
		}
		return result;
	}

	public static boolean isValidRepresentativeNumber(String mobile) {
		boolean result = false;
		String regex = "^1(?:5|6|8)(?:\\d{2})\\d{4}$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(mobile.trim());
		if (m.matches()) {
			result = true;
		}
		return result;
	}
}
