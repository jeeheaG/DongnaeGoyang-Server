package com.example.dongnaegoyangserver2022.global.security.user;

import com.example.dongnaegoyangserver2022.domain.member.dao.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Long kakaoId = Long.parseLong(username);
        return memberRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new RuntimeException("Member connected this kakao id is not exist. Please sign up."));  //TODO : 핸들링해서 응답에도 전달해주기);
    }
}
