package com.example.msg.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class KakaoUserService {
    private static final String AUTH_URL = "https://kauth.kakao.com/oauth/token";
    public static String authToken;
    private final CustomMessageService customMessageService;

    public boolean kakaoLogin(String code) throws JsonProcessingException {
        if(!getAccessToken(code)) {
            return false;
        }
        return customMessageService.sendMyMessage();
    }

    private boolean getAccessToken(String code) throws JsonProcessingException {

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "REST API 값을 입력하세요.");
        body.add("redirect_uri", "Redirect UIL을 입력하세요.");
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(AUTH_URL, HttpMethod.POST, kakaoTokenRequest, String.class);

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        if (responseBody.isEmpty()) {
            return false;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        authToken = jsonNode.get("access_token").asText();
        return true;
    }
}