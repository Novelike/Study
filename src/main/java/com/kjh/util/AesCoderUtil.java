package com.kjh.util;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * <pre>
 * 1. 프로젝트명 :
 * 2. 패키지명 : com.messagesend.util
 * 3. 작성일 : 2022-12-03
 * 4. 작성자 : 김남은
 * 5. 설명 :
 * </pre>
 */
public class AesCoderUtil {

	private static final String keyString = "mEssAgeSerVice#&(";

	// 암호화
	public static String encAES(String str) throws Exception {
		if (StringUtils.isEmpty(str)) {
			return "";
		}

		byte[] keyBytes = Arrays.copyOf(keyString.getBytes(StandardCharsets.US_ASCII), 16);

		SecretKey key = new SecretKeySpec(keyBytes, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, key);

		byte[] cleartext = str.getBytes(StandardCharsets.UTF_8);
		byte[] ciphertextBytes = cipher.doFinal(cleartext);

		String rtString = new String(Hex.encodeHex(ciphertextBytes));

		return rtString.toUpperCase();

	}

	// 복호화
	public static String decAES(String enStr) throws Exception {
		if (StringUtils.isEmpty(enStr)) {
			return "";
		}

		byte[] keyBytes = Arrays.copyOf(keyString.getBytes(StandardCharsets.US_ASCII), 16);

		SecretKey key = new SecretKeySpec(keyBytes, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, key);

		return new String(cipher.doFinal(Hex.decodeHex(enStr.toCharArray())));
	}
}
