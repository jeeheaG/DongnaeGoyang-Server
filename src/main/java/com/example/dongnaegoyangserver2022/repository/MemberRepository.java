package com.example.dongnaegoyangserver2022.repository;

import com.example.dongnaegoyangserver2022.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
