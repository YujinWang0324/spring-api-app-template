package com.app.api.tokentest;

import com.app.domain.member.constant.Role;
import com.app.global.jwt.dto.JwtTokenDto;
import com.app.global.jwt.service.TokenManager;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/token-test")
// 생성자 주입을 위한 어노테이션
@RequiredArgsConstructor
public class TokenTestController {

    // TokenManager Bean 주입
    private final TokenManager tokenManager;

    // jwt 토큰 생성
    @GetMapping("/create")
    public JwtTokenDto createJwtTokenDto() {
        return tokenManager.createJwtTokenDto(1L, Role.ADMIN);
    }

    // 토큰 유효 검증
    @GetMapping("/valid")
    public String validateJwtToken(@RequestParam String token) {
        tokenManager.validateToken(token);
        Claims tokenClaims = tokenManager.getTokenClaims(token);
        Long memberId = Long.valueOf((Integer)tokenClaims.get("memberId"));
        String role = (String) tokenClaims.get("role");
        log.info("memberId : {}", memberId);
        log.info("role : {}", role);

        return "success";
    }

}
