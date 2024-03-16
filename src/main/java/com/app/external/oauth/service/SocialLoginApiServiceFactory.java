package com.app.external.oauth.service;

import com.app.domain.member.constant.MemberType;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SocialLoginApiServiceFactory {

    private static Map<String, SocialLoginApiService> socialLoginApiServices;

    // 생성자 주입 <key : bean 이름, value : 각 social login api 서비스의 구현체>
    public SocialLoginApiServiceFactory(Map<String, SocialLoginApiService> socialLoginApiServices) {
        this.socialLoginApiServices = socialLoginApiServices;
    }

    public static SocialLoginApiService getSocialLoginApiService(MemberType memberType) {
        String socialLoginApiServiceBeanName = "";

        /*
            여러 social login 을 구현했을 때, 소스 변경 없이 해당 구현체를 사용할 수 있도록
            Member type 별로 지정
         */
        if(MemberType.KAKAO.equals(memberType)) {
            socialLoginApiServiceBeanName = "kakaoLoginApiServiceImpl";
        }
        return socialLoginApiServices.get(socialLoginApiServiceBeanName);
    }
}
