package com.kjh.mapper;

import com.kjh.dto.MessagePricingRequestDto;
import com.kjh.dto.MessageRequestDto;
import com.kjh.vo.*;
import com.kjh.vo.SearchVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface MessageMapper {

	// 메세지 로그 추가
	int insertMessageLog(MessageLogVO messageLogVO) throws Exception;

	// 메세지 로그 수정
	int updateMessageLog(MessageLogVO messageLogVO) throws Exception;

	// 메세지 로그 조회
	MessageLogVO selectMessageLog(MessageLogVO messageLogVO) throws Exception;

	// 저장 메세지함 저장
	int insertSaveMessageBox(SaveMessageBoxVO saveMessageBoxVO) throws Exception;

	// 저장 메세지함 조회
	List<SaveMessageBoxVO> selectSaveMessageBoxList(SearchVO searchVO) throws Exception;

	// 저장 메세지함 개수 조회
	int selectSaveMessageBoxListCnt(HashMap<String, Object> paramMap) throws Exception;

	// 저장 메세지함 삭제
	int deleteSaveMessageBox(SaveMessageBoxVO saveMessageBoxVO) throws Exception;

	// 메시지 건수 조회
	int countSendSms(HashMap<String, Object> paramMap) throws Exception;

	// 수신번호 추가
	int receiverAdd(HashMap<String, Object> paramMap) throws Exception;

	// 수신번호 조회
	List<ReceiverListVO> receiverList(SearchVO searchVO) throws Exception;

	// 수신번호 개수 조회
	int receiverListCnt(HashMap<String, Object> paramMap) throws Exception;

	// 수신번호 삭제
	int receiverDelete(HashMap<String, Object> paramMap) throws Exception;

	// 수신번호 전체 조회
	HashMap<String, Object> recvInfoLst(HashMap<String, Object> paramMap) throws Exception;

	// 연락처 수신번호 추가
	int contactReceiverAdd(HashMap<String, Object> paramMap) throws Exception;

	// 메시지 발송이력 조회
	List<MessageLogVO> selectMessageLogList(MessageRequestDto requestDto) throws Exception;

	// 메시지 발송이력 개수 조회
	Integer selectMessageLogCount(MessageRequestDto requestDto) throws Exception;

	Integer selectMessageLogCountAll(MessageRequestDto requestDto) throws Exception;

	// 메시지 타입별 이용요금 조회
	List<MessagePricingVO> selectMessagePricingList(MessagePricingRequestDto requestDto) throws Exception;

	// 메시지 타입별 건수 조회
	List<MessageLogVO> selectCountGroupByMessageType(MessageRequestDto requestDto) throws Exception;

	UserPricePolicyVO selectUserPricePolicy(MessagePricingRequestDto requestDto) throws Exception;
}
