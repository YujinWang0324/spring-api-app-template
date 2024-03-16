package com.app.domain.member.entity;

import com.app.domain.common.BaseEntity;
import com.app.domain.member.constant.MemberType;
import com.app.domain.member.constant.Role;
import com.app.global.jwt.dto.JwtTokenDto;
import com.app.global.util.DateTimeUtils;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity // Entity 클래스 임을 알려주는 어노테이션
@Getter
/*
    Entity 클래스는 항상 기본 생성자가 있어야 하기 때문에 기본 생성자를 만들어주는 어노테아션 추가
    객체의 무분별한 생성을 피하기 위해 AccessLevel.PROTECTED
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // GenerationType.IDENTITY : 기본키 생성 전략을 데이터 베이스에 맡김
    private Long memberId;

    /*
     Enum 을 필드로 추가할 때 EnumType.STRING 으로 지정해줘야 Enum 의 'KAKAO' 라는 값이 그대로 들어감
     기본 값은 ORDINAL 이기 때문에 순서가 들어감
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private MemberType memberType;

    @Column(unique = true, length = 50, nullable = false)
    private String email;

    @Column(length = 200)
    private String password;

    @Column(nullable = false, length = 20)
    private String memberName;

    @Column(length = 200)
    private String profile;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Role role;

    @Column(length = 250)
    private String refreshToken;

    private LocalDateTime tokenExpirationTime;

    @Builder
    public Member(MemberType memberType, String email, String password, String memberName,
                  String profile, Role role) {
        this.memberType = memberType;
        this.email = email;
        this.password = password;
        this.memberName = memberName;
        this.profile = profile;
        this.role = role;
    }

    public void updateRefreshToken(JwtTokenDto jwtTokenDto) {
        this.refreshToken = jwtTokenDto.getRefreshToken();
        this.tokenExpirationTime = DateTimeUtils.convertToLocalDateTime(jwtTokenDto.getRefreshTokenExpireTime());
    }

    public void expireRefreshToken(LocalDateTime now) {
        this.tokenExpirationTime = now;
    }
}
