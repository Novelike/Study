package com.kjh.pcc;

import com.kjh.service.UserService;
import com.sci.v2.pccv2.secu.SciSecuManager;
import com.sci.v2.pccv2.secu.hmac.SciHmac;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

@Slf4j
@Controller
@RequestMapping("/pcc")
public class PccController {

	private @Value("STIS001") String id;    // STIP003
	private @Value("002001") String srvNo;   // 053002

	private @Value("http://localhost:9090") String isDomainNm;   // 리턴url 도메인

	@Autowired
	private UserService userService;

	@RequestMapping("/requestSeed")
	public String requestSeed(HttpServletRequest request, HttpServletResponse response, Model model
		, String checkmode) {

		if (checkmode == null || checkmode.equals("")) {
			checkmode = "check";
		}

		Calendar today = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String day = sdf.format(today.getTime());
		Random ran = new Random();
		int numLength = 6;
		String randomStr = "";
		for (int i = 0; i < numLength; i++) {
			//0 ~ 9 랜덤 숫자 생성
			randomStr += ran.nextInt(10);
		}

		String reqNum = day + randomStr;       // 본인실명확인 요청번호
		String exVar = "0000000000000000";  // 복호화용 임시필드
		log.info("reqNum = " + reqNum);
		String retUrl = "32" + isDomainNm + "/pcc/resultSeed?checkmode=" + checkmode;

		log.info("retUrl >>> {}", retUrl);

		Cookie c = new Cookie("reqNum", reqNum);
		//c.setMaxAge(1800);  // <== 필요시 설정(초단위로 설정됩니다)
		response.addCookie(c);

		//HttpSession session = request.getSession();
		//session.setAttribute("reqNum", reqNum);

		String certDate = day;
		String certGb = "H";                // 본인실명확인 본인확인 인증수단
		String addVar = "";                   // 본인실명확인 추가 파라메터

		//01. 암호화 모듈 선언
		SciSecuManager seed = new SciSecuManager();

		//02. 1차 암호화
		String encStr = "";
		String reqInfo =
			id + "^" + srvNo + "^" + reqNum + "^" + certDate + "^" + certGb + "^" + addVar + "^" + exVar;  // 데이터 암호화

		seed.setInfoPublic(id, "37D14F799CFD6BB79CF72BBE8A0F529A");  //bizsiren.com > 회원사전용 로그인후 확인.

		encStr = seed.getEncPublic(reqInfo);

		//03. 위변조 검증 값 생성
		//com.sci.v2.pcc.secu.hmac.SciHmac hmac = new com.sci.v2.pcc.secu.hmac.SciHmac();
		SciHmac hmac = new SciHmac();

		//String hmacMsg = hmac.HMacEncriptPublic(encStr);
		String hmacMsg = seed.getEncReq(encStr, "HMAC");

		//03. 2차 암호화
		reqInfo = seed.getEncPublic(encStr + "^" + hmacMsg + "^" + "0000000000000000");  //2차암호화

		//04. 회원사 ID 처리를 위한 암호화
		reqInfo = seed.EncPublic(reqInfo + "^" + id + "^" + "00000000");

		model.addAttribute("reqInfo", reqInfo);
		model.addAttribute("retUrl", retUrl);

		return "pcc/requestSeed";
	}

	@RequestMapping("/resultSeed")
	public String resultSeed(HttpServletRequest request, HttpServletResponse response, Model model
		, String checkmode
	) {
		try {
			// Parameter 수신 --------------------------------------------------------------------
			String retInfo = request.getParameter("retInfo").trim(); //반드시 get과 post 방식 둘 다 받을수있게 허용해놔야함.
			log.info("retInfo = " + retInfo);

			Cookie[] cookies = request.getCookies();
			String cookiename = "";
			String cookiereqNum = "";
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					Cookie c = cookies[i];
					cookiename = c.getName();
					cookiereqNum = c.getValue();
					if (cookiename.compareTo("reqNum") == 0)
						break;

					cookiereqNum = null;
				}
			}
			log.info("cookiereqNum = " + cookiereqNum);

			String reqNum = cookiereqNum;

			String msgChk = "";

			// 1. 암호화 모듈 (jar) Loading
			//com.sci.v2.pccv2.secu.SciSecuManager sciSecuMg = new com.sci.v2.pccv2.secu.SciSecuManager();
			SciSecuManager sciSecuMg = new SciSecuManager();
			sciSecuMg.setInfoPublic(id, "37D14F799CFD6BB79CF72BBE8A0F529A");  //bizsiren.com > 회원사전용 로그인후 확인.

			// 3. 1차 파싱---------------------------------------------------------------
			retInfo = sciSecuMg.getDec(retInfo, reqNum);

			log.info("retInfo = " + retInfo);
			// 4. 요청결과 복호화
			String[] aRetInfo1 = retInfo.split("\\^");

			String encPara = aRetInfo1[0];         //암호화된 통합 파라미터
			String encMsg = aRetInfo1[1];    //암호화된 통합 파라미터의 Hash값

			String encMsg2 = sciSecuMg.getMsg(encPara);

			// 5. 위/변조 검증 ---------------------------------------------------------------
			if (encMsg2.equals(encMsg)) {
				msgChk = "Y";
			}

			if (msgChk.equals("N")) {
				System.out.println(" HMAC 확인이 필요합니다. ");
			}

			retInfo = sciSecuMg.getDec(encPara, reqNum);

			String[] aRetInfo = retInfo.split("\\^");

			model.addAttribute("info", aRetInfo);

			log.info("retInfo = " + retInfo);
			if (checkmode == null) {
				checkmode = "";
			}
			model.addAttribute("checkmode", checkmode);

			log.info("checkmode = " + checkmode);

			if (checkmode.equals("modify")) {
				HashMap<String, Object> uvo = new HashMap<>();
				int result = userService.userPccCheck(aRetInfo, uvo);
				if (result == 10) {
					model.addAttribute("checkmode", "modJoinMember");
					model.addAttribute("checkId", uvo.get("user_id"));
				}
			} else if (checkmode.equals("insert")) {
				HashMap<String, Object> uvo = new HashMap<>();
				int result = userService.userPccCheck(aRetInfo, uvo);
				if (result == 10) {
					model.addAttribute("checkmode", "joinMember");
					model.addAttribute("checkId", uvo.get("user_mobile"));
				}
			} else if (checkmode.equals("findinfo")) {
				model.addAttribute("checkmode", "findinfo");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "pcc/resultSeed";
	}
}
