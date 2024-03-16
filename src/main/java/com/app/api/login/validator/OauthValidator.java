package com.app.api.login.validator;

import com.app.domain.member.constant.MemberType;
import com.app.global.error.ErrorCode;
import com.app.global.error.exception.AuthenticationException;
import com.app.global.error.exception.BusinessException;
import com.app.global.jwt.constant.GrantType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class OauthValidator {

    public void validateAuthorization(String authorizationHeader) {

        // 1. authorizationHeader 필수 체크
        if(!StringUtils.hasText(authorizationHeader)) {
            throw new AuthenticationException(ErrorCode.NOT_EXISTS_AUTHORIZATION);
        }

        // 2. authorizationHeader Bearer 체크
        String[] authorizations = authorizationHeader.split(" "); // Bearer + " " + kakao access token 으로 옴
        if(authorizations.length < 2 || (!GrantType.BEARER.getType().equals(authorizations[0]))) {
            throw new AuthenticationException(ErrorCode.NOT_VALID_BEARER_GRANT_TYPE);
        }
    }

    public void validateMemberType(String memberType) {
        if(!MemberType.isMemberType(memberType)) {
            throw new BusinessException(ErrorCode.INVALID_MEMBER_TYPE);
        }
    }
}
