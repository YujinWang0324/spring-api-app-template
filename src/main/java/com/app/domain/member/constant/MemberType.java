package com.app.domain.member.constant;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum MemberType {

    KAKAO;

    public static MemberType from(String type) {
        return MemberType.valueOf(type.toUpperCase()); // type 을 대문자로 변경
    }

    public static boolean isMemberType(String type) {

        List<MemberType> memberTypes = Arrays.stream(MemberType.values())
                .filter(memberType -> memberType.name().equals(type)) // type 과 이름이 같은 것만을 가져옴
                .collect(Collectors.toList()); // 결과를 List 로 반환
        // memberTypes 는 하나의 결과를 가지고 있어야 되기 때문에 size 가 0이 아니면 true, 해당하는 멤버 타입이 없다면 0 (false)
        return memberTypes.size() != 0;

    }

}
