package com.app.web.kakaotoken.client;

import com.app.web.kakaotoken.dto.KakaoTokenDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(url = "https://kauth.kakao.com", name = "kakaoTokenClient")
public interface KakaoTokenClient {
    /*
        consumes : 클라이언트가 서버에게 보내는 데이터 타입을 명시
        produces : 서버가 클라이언트에게 반환하는 데이터 타입을 명시
     */
    @PostMapping(value = "/oauth/token", consumes = "application/json")
    KakaoTokenDto.Response requestKakaoToken(@RequestHeader("Content-Type") String contentType,
                                             @SpringQueryMap KakaoTokenDto.Request request
    );

}
