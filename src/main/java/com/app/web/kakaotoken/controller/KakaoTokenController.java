package com.app.web.kakaotoken.controller;

import com.app.web.kakaotoken.client.KakaoTokenClient;
import com.app.web.kakaotoken.dto.KakaoTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class KakaoTokenController {

    private final KakaoTokenClient kakaoTokenClient;

    // @Value 어노테이션으로 yml 파일에 명시해둔 값 가져오기
    @Value("${kakao.client.id}")
    private String clientId;

    @Value("${kakao.client.secret}")
    private String clientSecret;

    @GetMapping("/login")
    public String login() {
        return "loginForm";
    }

    @GetMapping("/oauth/kakao/callback")
    public @ResponseBody String loginCallback(String code) {
        String contentType = "application/x-www-form-urlencoded;charset=utf-8"; // 헤더 요청 값
        KakaoTokenDto.Request kakaoTokenRequestDto = KakaoTokenDto.Request.builder() // 본문 요청 값
                .client_id(clientId)
                .client_secret(clientSecret)
                .grant_type("authorization_code")
                .code(code) // 받아온 인가코드
                .redirect_uri("http://localhost:8080/oauth/kakao/callback")
                .build();
        KakaoTokenDto.Response kakaoToken = kakaoTokenClient.requestKakaoToken(contentType, kakaoTokenRequestDto);
        return "kakao toekn : " + kakaoToken;
    }

}
