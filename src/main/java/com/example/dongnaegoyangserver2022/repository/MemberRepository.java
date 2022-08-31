package com.example.dongnaegoyangserver2022.repository;

import com.example.dongnaegoyangserver2022.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByKakaoId(Long kakaoId); //TODO : 흠..
}
