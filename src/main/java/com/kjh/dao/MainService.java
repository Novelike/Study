package com.kjh.dao;

import com.kjh.dto.ResultDTO;
import com.kjh.vo.SessionVO;
import com.kjh.vo.UserMenuVO;
import com.kjh.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 * 1. 프로젝트명 : 문자 서비스
 * 2. 패키지명 : com.messagesend.service
 * 3. 작성일 : 2022-12-03
 * 4. 작성자 : 김남은
 * 5. 설명 :
 * </pre>
 */
@Service
public class MainService {

	private static final Logger log = LoggerFactory.getLogger(MainService.class);

	@Autowired
	MenuService menuService;

	public boolean setSession(HttpServletRequest request, UserVO userMap, String strToken) {
		SessionVO svo = new SessionVO();
		svo.setToken(strToken);
		svo.setUser(userMap);

		List<UserMenuVO> userMenuVOList = new ArrayList<UserMenuVO>();

		//해당 계정 그룹이 사용할 수 있는 메뉴 리스트를 추출
		try {
			int userSeq = 0;
			if (userMap != null) {
				userSeq = userMap.getSeq();
			}
			ResultDTO resultDTO = menuService.menuSearch(userSeq);
			if ("000".equals(resultDTO.getCode())) {
				HashMap<String, Object> param = new HashMap<String, Object>();
				param = (HashMap<String, Object>)resultDTO.getData();

				userMenuVOList = (List<UserMenuVO>)param.get("userMenuList");
			} else {

			}
			svo.setUserMenuList(userMenuVOList);

			List<UserMenuVO> temp = new ArrayList<UserMenuVO>();
			boolean bFlag = false;
			for (UserMenuVO userMenuVO : userMenuVOList) {
				if (userMenuVO.getSub1Code() == 0 && userMenuVO.getSub2Code() == 0) {
					temp.add(userMenuVO);

					if (!bFlag) {
						svo.setMainRedirectUrl(userMenuVO.getMenuUrl());
						bFlag = true;
					}
				}
			}

			svo.setFirstMenuList(temp.stream().distinct().collect(Collectors.toList()));

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		request.getSession().setAttribute("sessionVO", svo);
		return true;
	}
}
