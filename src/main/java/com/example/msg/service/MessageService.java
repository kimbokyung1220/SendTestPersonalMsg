package com.example.msg.service;

import com.example.msg.dto.DefaultMessageDto;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class MessageService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final String MSG_SEND_SERVICE_URL = "https://apis.moment.kakao.com/openapi/v4/messages/creatives/{소재ID}/sendTestPersonalMessage";
	private static final String SEND_SUCCESS_MSG = "메시지 전송에 성공했습니다.";
	private static final String SEND_FAIL_MSG = "메시지 전송에 실패했습니다.";
	private static final String SUCCESS_CODE = "200"; //kakao api에서 return해주는 success code 값

	public boolean sendMessage(String accessToken, DefaultMessageDto msgDto) {
		// HTTP 헤더
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("adAccountId", "광고주ID를 입력해 주세요.");

		// HTTP 바디 (JSON)
		JSONObject variables = new JSONObject();
		variables.put("date1", msgDto.getDate1());
		variables.put("date2", msgDto.getDate2());
		variables.put("site_name1", msgDto.getSite_name1());
		variables.put("user_name1", msgDto.getUser_name1());
		variables.put("mobile_url1", msgDto.getMobile_url1());
		variables.put("mobile_url2", msgDto.getMobile_url2());
		variables.put("pc_url1", msgDto.getPc_url1());
		variables.put("pc_url2", msgDto.getPc_url2());

		JSONObject body = new JSONObject();
		body.put("phoneNumber", msgDto.getPhoneNumber());
		body.put("variables", variables);

		String resultCode = "";
		try {
		// HTTP 요청 보내기
		HttpEntity<String> sendMessgeInfo = new HttpEntity<>(body.toString(), headers);
		RestTemplate rt = new RestTemplate();
		ResponseEntity<String> response = rt.exchange(MSG_SEND_SERVICE_URL, HttpMethod.POST, sendMessgeInfo, String.class);
		resultCode = String.valueOf(response.getStatusCodeValue());
		} catch (Exception e) {
			resultCode = "500"; // 예외가 발생했을 경우에 대한 기본값 설정
		}

		return successCheck(resultCode);
	}

	public boolean successCheck(String resultCode) {
		if(resultCode.equals(SUCCESS_CODE)) {
			return true;
		}else {
			logger.debug(SEND_FAIL_MSG);
			return false;
		}
	}
}
