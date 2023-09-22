package com.kjh.mapper;

import com.kjh.vo.SearchVO;
import com.kjh.vo.SendNumberVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface SendNumberMapper {

	// 발신번호 조회
	SendNumberVO selectSendNumber(HashMap<String, Object> param) throws Exception;

	// 발신번호 추가
	int insertSendNumber(SendNumberVO sendNumberVO) throws Exception;

	// 발신번호 수정
	int updateSendNumber(SendNumberVO sendNumberVO) throws Exception;

	// 발신번호 삭제
	int deleteSendNumber(HashMap<String, Object> param) throws Exception;

	// 발신번호 파일 저장
	int insertSendNumberFile(HashMap<String, Object> param) throws Exception;

	// 발신번호 리스트 조회
	List<SendNumberVO> selectSendNumberList(HashMap<String, Object> param) throws Exception;

	// 발신번호 리스트 개수
	int selectSendNumberListCnt(HashMap<String, Object> param) throws Exception;

	// 발신번호 파일 삭제
	int deleteSendNumberFile(HashMap<String, Object> param) throws Exception;

	// 발신번호 리스트 조회
	List<SendNumberVO> searchSendNumberList(SearchVO searchVO) throws Exception;

}
