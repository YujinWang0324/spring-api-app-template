package com.app.domain.member.repository;

import com.app.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // jpa 에서 제공하는 메소드 기능에 의해서 이메일 기반으로 멤버를 찾음
    Optional<Member> findByEmail(String email);

    Optional<Member> findByRefreshToken(String refreshToken);
}
