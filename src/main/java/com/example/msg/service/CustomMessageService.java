package com.example.msg.service;

import com.example.msg.dto.DefaultMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomMessageService {
    private final MessageService messageService;

    public boolean sendMyMessage() {
        DefaultMessageDto myMsg = new DefaultMessageDto();
        myMsg.setPhoneNumber("개인화 메세지를 받을 사람의 핸드폰 번호를 입력하세요.");
        myMsg.setDate1("날짜를 입력하세요.");
        myMsg.setDate2("날짜를 입력하세요.");
        myMsg.setSite_name1("사이트명을 입력하세요.");
        myMsg.setUser_name1("개인화 메세지를 받을 사람의 이름을 입력하세요.");
        myMsg.setMobile_url1("https://m.naver.com/");
        myMsg.setMobile_url2("https://m.naver.com/");
        myMsg.setPc_url1("https://naver.com/");
        myMsg.setPc_url2("https://naver.com/");

        String accessToken = KakaoUserService.authToken;
        return messageService.sendMessage(accessToken, myMsg);
    }
}
