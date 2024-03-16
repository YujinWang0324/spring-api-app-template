package com.app.external.oauth.kakao.service;

import com.app.domain.member.constant.MemberType;
import com.app.external.oauth.kakao.client.KakaoUserInfoClient;
import com.app.external.oauth.kakao.dto.KakaoUserInfoResponseDto;
import com.app.external.oauth.model.OAuthAttributes;
import com.app.external.oauth.service.SocialLoginApiService;
import com.app.global.jwt.constant.GrantType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class KakaoLoginApiServiceImpl implements SocialLoginApiService {

    // Bean
    private final KakaoUserInfoClient kakaoUserInfoClient;

    // 상수
    private final String CONTENT_TYPE = "application/x-www-form-urlencoded;charset=utf-8";

    @Override
    public OAuthAttributes getUserInfo(String accessToken) {
        KakaoUserInfoResponseDto kakaoUserInfoResponseDto = kakaoUserInfoClient.getKakaoUserInfo(CONTENT_TYPE,
                GrantType.BEARER.getType() + " " + accessToken);
        KakaoUserInfoResponseDto.KakaoAccount kakaoAccount = kakaoUserInfoResponseDto.getKakaoAccount();
        String email = kakaoAccount.getEmail();

        return OAuthAttributes.builder()
                .email(!StringUtils.hasText(email) ? kakaoUserInfoResponseDto.getId() : email) // 이메일이 없는 경우는 id 저장, 있는 경우 email 저장
                .name(kakaoAccount.getProfile().getNickname())
                .profile(kakaoAccount.getProfile().getThumbnailImageUrl())
                .memberType(MemberType.KAKAO)
                .build();
    }
}
