package com.app.api.token.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter @Builder
public class AccessTokenResponseDto {

    @Schema(description = "grantType", example = "Bearer", required = true)
    private String grantType;

    @Schema(description = "accessToken", example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBQ0NFU1MiLCJpYXQiOjE3MTAwNjgzNjIsImV4cCI6MTcxMDA2OTI2MiwibWVtYmVySWQiOjEsInJvbGUiOiJBRE1JTiJ9.lpE5ut6U1OEIunm3DKRY6z_-QDkZo5fPm5984RZdg0TdxTI9HbVH3stjPJplv3kefNAVB0gariAIkCI5GyS1MA", required = true)
    private String accessToken;

    @Schema(description = "access token 만료 시간", example = "2024-03-10 20:14:22", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date accessTokenExpireTime;

}
