package com.example.dongnaegoyangserver2022.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
//@Setter
//@DynamicUpdate
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "member")
@Entity
public class Member { //NotNull인 게 하나도 없음.. 일단 kakaoId를 NotNull로 하는데 개선 필요
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

    @Column(nullable = false)
    private Long kakaoId; //카카오 회원번호(고유값)
}
