package com.kjh.util;

import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.jwk.JWKSet;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * 1. 프로젝트명 :
 * 2. 패키지명 : com.messagesend.util
 * 3. 작성일 : 2022-12-03
 * 4. 작성자 : 김남은
 * 5. 설명 :
 * </pre>
 */
@Slf4j
@Component
public class Token {
	private JWKSet jwkSet;
	private NimbusJwtEncoder encoder;
	private NimbusJwtDecoder decoder;

	public String tokenEncode(Map<String, Object> tokenMap) throws Exception {
		this.jwkSet = JWKSet.load(
			new ClassPathResource("/JWKKeypairSet.json", this.getClass().getClassLoader()).getInputStream());
		this.encoder = new NimbusJwtEncoder(jwkSet);

		String tokenValue = "tokenValue";
		Instant issuedAt = Instant.now();
		Instant expiresAt = Instant.from(issuedAt).plusSeconds(60 * 60 * 24 * 365);

		Map<String, Object> headers = new HashMap<>();
		headers.put("typ", "JWT");
		headers.put("alg", "RS256");

		Map<String, Object> claims = new LinkedHashMap<>();
		claims.put("userSeq", tokenMap.get("userSeq"));
		claims.put("userId", tokenMap.get("userId"));
		claims.put("userName", tokenMap.get("userName"));
		claims.put("userType", tokenMap.get("userType"));
		claims.put("userStatus", tokenMap.get("userStatus"));

		String encodedToken = encoder.encode(new Jwt(tokenValue, issuedAt, expiresAt, headers, claims));
		log.info("encodedToken : {}", encodedToken);
		return encodedToken;
	}

	public Map<String, Object> tokenDecode(String authorization) throws Exception {
		String token = authorization.replaceAll("(?i)bearer", "").trim();

		this.jwkSet = JWKSet.load(
			new ClassPathResource("/JWKKeypairSet.json", this.getClass().getClassLoader()).getInputStream());

		this.decoder = new NimbusJwtDecoder(jwkSet);

		log.info("claims : {}", decoder.decode(token).getClaims());
		return decoder.decode(token).getClaims();
	}
}
