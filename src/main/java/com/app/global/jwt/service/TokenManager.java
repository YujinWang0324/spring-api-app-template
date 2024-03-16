package com.app.global.jwt.service;

import com.app.domain.member.constant.Role;
import com.app.global.error.ErrorCode;
import com.app.global.error.exception.AuthenticationException;
import com.app.global.jwt.constant.GrantType;
import com.app.global.jwt.constant.TokenType;
import com.app.global.jwt.dto.JwtTokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class TokenManager {

    // yml 파일에 지정해준 값을 가져옴
    private final String accessTokenExpirationTime;
    private final String refreshTokenExpirationTime;
    private final String tokenSecret;

    public JwtTokenDto createJwtTokenDto(Long memberId, Role role) {
        Date accessTokenExpireTime = createAccessTokenExpireTime();
        Date refreshTokenExpireTime = createRefreshTokenExpireTime();

        String accessToken = createAccessToken(memberId, role, accessTokenExpireTime);
        String refreshToken = createRefreshToken(memberId, refreshTokenExpireTime);

        return JwtTokenDto.builder()
                .grantType(GrantType.BEARER.getType())
                .accessToken(accessToken)
                .accessTokenExpireTime(accessTokenExpireTime)
                .refreshToken(refreshToken)
                .refreshTokenExpireTime(refreshTokenExpireTime)
                .build();
    }

    // acess token 만료 시간
    public Date createAccessTokenExpireTime() {
        // 현재 시간으로 부터 15분 뒤 시간을 반환
        return new Date(System.currentTimeMillis() + Long.parseLong(accessTokenExpirationTime));
    }

    // refresh token 만료 시간
    public Date createRefreshTokenExpireTime() {
        // 현재 시간으로 부터 2주 뒤 시간을 반환
        return new Date(System.currentTimeMillis() + Long.parseLong(refreshTokenExpirationTime));
    }

    // access token 발급
    public String createAccessToken(Long memberId, Role role, Date expirationTime) {
        String accessToken = Jwts.builder()
                .setSubject(TokenType.ACCESS.name())        // 토큰 제목
                .setIssuedAt((new Date()))                  // 토큰 발급 시간 (현재 시간)
                .setExpiration((expirationTime))            // 토큰 만료 시간
                .claim("memberId", memberId)             // 회원 아이디
                .claim("role", role)                     //유저 role
                .signWith(SignatureAlgorithm.HS512, tokenSecret.getBytes(StandardCharsets.UTF_8))
                .setHeaderParam("typ", "JWT")
                .compact();
        return accessToken;
    }

    // refresh token 발급
    public String createRefreshToken(Long memberId, Date expirationTime) {
        String refreshToken = Jwts.builder()
                .setSubject(TokenType.REFRESH.name())       // 토큰 제목
                .setIssuedAt((new Date()))                  // 토큰 발급 시간 (현재 시간)
                .setExpiration((expirationTime))            // 토큰 만료 시간
                .claim("memberId", memberId)             // 회원 아이디
                .signWith(SignatureAlgorithm.HS512, tokenSecret.getBytes(StandardCharsets.UTF_8))
                .setHeaderParam("typ", "JWT")
                .compact();
        return refreshToken;
    }

    // 올바른 토큰인지 검증 메소드
    public void validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(tokenSecret.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token); // token 을 파싱해주면 올바른 토큰이 아닐 때 예외 발생
        } catch (ExpiredJwtException e) { // 토큰이 만료 됐을 때 발생하는 예외
            log.info("토큰 만료", e);
            throw new AuthenticationException(ErrorCode.TOKEN_EXPIRED);
        } catch (Exception e) { // 위변조 된 토큰, 발급하지 않은 토큰이 들어왔을 때 발생하는 예외
            log.info("유효하지 않은 token", e);
            throw new AuthenticationException(ErrorCode.NOT_VALID_TOKEN);
        }
    }

    // 토큰이 들어왔을 때 페이로드에 있는 클레임 정보를 가지고 오는 메소드
    public Claims getTokenClaims(String token) {
        Claims claims;
        try { // 토큰 페이로드에 담겨있는 key, value 한 쌍 = claim 정보
            claims = Jwts.parser().setSigningKey(tokenSecret.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token).getBody();
        } catch (Exception e) {
            log.info("유효하지 않은 token", e);
            throw new AuthenticationException(ErrorCode.NOT_VALID_TOKEN);
        }
        return claims;
    }

}
