package com.app.global.config;

import com.app.global.error.FeignClientExceptionErrorDecoder;
import feign.Logger;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableFeignClients(basePackages = "com.app") // todo 패키지명 수정
@Import(FeignClientsConfiguration.class)
public class FeignConfiguration {

    @Bean
    Logger.Level feignLoggerLevel() {
        /*
        • NONE : 로깅 X
        • BASIC : 요청 메서드와 URL, 응답 상태 코드, 실행시간을 로깅
        • HEADERS : BASIC 단계의 로깅에서 추가로 request 와 response 의 headers 로깅
        • FULL : request, response 의 headers, body, metadata 를 모두 로깅
         */
        return Logger.Level.FULL;
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignClientExceptionErrorDecoder();
    }

    @Bean
    public Retryer retryer() {
        return new Retryer.Default(1000, 2000, 3); // period : 실행주기, maxAttempts: 최대 재시도 횟수
    }
}
