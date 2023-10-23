package com.example.msg.controller;

import com.example.msg.service.KakaoUserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class UserController {
    private final KakaoUserService kakaoUserService;

    // home 화면
    @GetMapping("/")
    public String home() {
        return "login";
    }

    // 회원 로그인 페이지
    @GetMapping("/user/login")
    public String login() {
        return "login";
    }

    @GetMapping("/dev/kakaoApiTest")
    public ModelAndView kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        boolean success = kakaoUserService.kakaoLogin(code);
        ModelAndView modelAndView = new ModelAndView("result");
        modelAndView.addObject("success", success);
        return modelAndView;
    }
}