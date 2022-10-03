package com.example.dongnaegoyangserver2022.domain.member.domain;

import com.example.dongnaegoyangserver2022.domain.member.dto.MemberResponse;
import com.example.dongnaegoyangserver2022.global.common.ModelMapperUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
//@Setter
//@DynamicUpdate
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "member")
@Entity
public class Member implements UserDetails { //NotNull인 게 하나도 없음.. 일단 kakaoId를 NotNull unique로 하는데 개선 필요
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberIdx;

    @Column(length = 100) //null 가능(카카오에서 필수 수집 불가)
    private String email;

    @Column(length = 100) //, nullable = false //카카오 로그인 이용
    private String password;

    @Column(length = 100) //, nullable = false //굳이? 카카오 닉네임이 없는 경우가 있다면 골치아파짐
    private String nickname;

    private String sido;

    private String gugun;

    @Column(length = 100, nullable = false)
    private String login_type;

    @Column(nullable = false, unique = true)
    private Long kakaoId; //카카오 회원번호(고유값)

    //-- to response dto 메서드--//

    public MemberResponse.MemberSimpleResponse toMemberSimpleResponse() {
        return ModelMapperUtil.getModelMapper().map(this, MemberResponse.MemberSimpleResponse.class);
    }


    ////////////////////////////// implements UserDetails //////////////////////////////

    //TODO : ??? roles가 그래서 어디서 오는 거임??
    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return kakaoId.toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
