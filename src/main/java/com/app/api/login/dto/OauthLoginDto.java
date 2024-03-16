package com.app.api.login.dto;

import com.app.global.jwt.dto.JwtTokenDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;

public class OauthLoginDto {

    @Getter @Setter
    public static class Request {
        @Schema(description = "소셜 로그인 회원 타입", example = "KAKAO", required = true)
        private String memberType;
    }

    @Getter @Setter
    @Builder @NoArgsConstructor @AllArgsConstructor
    public static class Response {

        @Schema(description = "grantType", example = "Bearer", required = true)
        private String grantType;

        @Schema(description = "accessToken", example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBQ0NFU1MiLCJpYXQiOjE3MTAwNjgzNjIsImV4cCI6MTcxMDA2OTI2MiwibWVtYmVySWQiOjEsInJvbGUiOiJBRE1JTiJ9.lpE5ut6U1OEIunm3DKRY6z_-QDkZo5fPm5984RZdg0TdxTI9HbVH3stjPJplv3kefNAVB0gariAIkCI5GyS1MA", required = true)
        private String accessToken;

        @Schema(description = "access token 만료 시간", example = "2024-03-10 20:14:22", required = true)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private Date accessTokenExpireTime;

        @Schema(description = "refreshToken", example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJSRUZSRVNIIiwiaWF0IjoxNzEwMDY4MzYyLCJleHAiOjE3MTEyNzc5NjIsIm1lbWJlcklkIjoxfQ.UFBQ_jLaqgugs3TQq4MvozGnhiHRV0FQDXHlHzDRFo3-JGFZ-9Ww8K9ESqmL7Bca8LSGzaSMHxSG-jqpx3R9lQ", required = true)
        private String refreshToken;

        @Schema(description = "refresh token 만료 시간", example = "2024-03-24 19:59:22", required = true)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private Date refreshTokenExpireTime;

        public static Response of(JwtTokenDto jwtTokenDto) {
            return Response.builder()
                    .grantType(jwtTokenDto.getGrantType())
                    .accessToken(jwtTokenDto.getAccessToken())
                    .accessTokenExpireTime(jwtTokenDto.getAccessTokenExpireTime())
                    .refreshToken(jwtTokenDto.getRefreshToken())
                    .refreshTokenExpireTime(jwtTokenDto.getRefreshTokenExpireTime())
                    .build();

        }
    }
}
