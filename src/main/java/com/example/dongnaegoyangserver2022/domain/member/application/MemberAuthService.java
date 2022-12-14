package com.example.dongnaegoyangserver2022.domain.member.application;

import com.example.dongnaegoyangserver2022.global.config.exception.CustomException;
import com.example.dongnaegoyangserver2022.global.config.exception.RestApiException;
import com.example.dongnaegoyangserver2022.global.config.exception.error.MemberErrorCode;
import com.example.dongnaegoyangserver2022.global.config.jwt.JwtTokenProvider;
import com.example.dongnaegoyangserver2022.domain.member.domain.Member;
import com.example.dongnaegoyangserver2022.domain.member.dto.MemberRequest;
import com.example.dongnaegoyangserver2022.domain.member.dto.MemberResponse;
import com.example.dongnaegoyangserver2022.domain.member.dao.MemberRepository;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberAuthService {
    // TODO : kakaoId 형변환 없이 전달 흐름 수정해보기, UserPK네이밍 바꾸기??
    // TODO : 추후 비즈니스 로직을 도메인으로 이동시키기

//    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    private final MemberRepository memberRepository;

    public Member getMemberByHeader(HttpServletRequest servletRequest) throws RestApiException {
        Long kakaoId = jwtTokenProvider.getUserPKByServlet(servletRequest);
        if(kakaoId == null){
            //비회원
            log.info("[REJECT] getMemberByHeader");
            throw new RestApiException(MemberErrorCode.GUEST_USER);
        }

        // DB확인
        Optional<Member> memberOptional = memberRepository.findByKakaoId(kakaoId);
        if(memberOptional.isEmpty()){
            log.info("[REJECT] getMemberByHeader : No member of kakao id = "+ kakaoId);
            throw new RestApiException(MemberErrorCode.NOT_EXIST_KAKAO_MEMBER);
        }

        return memberOptional.get();
    }

    public void deleteAccount(Member member){
        memberRepository.delete(member);
    }

    public String checkTokenInfo(HttpServletRequest servletRequest){
        Long kakaoId = jwtTokenProvider.getUserPKByServlet(servletRequest);
        if(kakaoId == null){
            log.info("[REJECT] checkTokenInfo");
            throw new RestApiException(MemberErrorCode.GUEST_USER);
        }

        boolean valid = jwtTokenProvider.validateTokenByServlet(servletRequest);

        return "kakaoId : "+kakaoId+" / valid : "+valid;
    }

    public MemberResponse.LoginResponse login(HttpServletRequest httpServletRequest, MemberRequest.LoginRequest request){
        if(!request.getLoginType().equals("kakao")){
            System.out.println("loginType : "+ request.getLoginType());
            throw new CustomException(HttpStatus.CONFLICT, "Login type error.");
        }

        String kakaoToken = httpServletRequest.getHeader("Authorization");
        if(kakaoToken == null){
            System.out.println("kakaoToken : "+kakaoToken);
            throw new CustomException(HttpStatus.UNAUTHORIZED, "KakaoToken is null. Need \"Authorization\" header.");
        }
        Long kakaoId = (Long) getKakaoInfo(kakaoToken, "login").get("id");

        System.out.println("kakaoId : "+kakaoId);

        // DB확인하고 거절 or 토큰발급
        Optional<Member> memberOptional = memberRepository.findByKakaoId(kakaoId);
        if(memberOptional.isEmpty()){
            System.out.println("No member, kakao id : "+ kakaoId);
            throw new RestApiException(MemberErrorCode.NOT_EXIST_KAKAO_MEMBER);
        }

//        System.out.println("member : "+memberOptional);
//        System.out.println("member.get : "+memberOptional.get());
//        System.out.println("member.get.getRoles : "+memberOptional.get().getRoles());

//        Optional<Member> member = memberRepository.findByKakaoId(kakaoId)
//                .orElseThrow(() -> new RuntimeException("Member connected this kakao id is not exist. Please sign up."));
//        if(member == null){
//            System.out.println("No member, kakao id : "+ kakaoId);
//            throw new RuntimeException("Member connected this kakao id is not exist. Please sign up.");
//        }

        Member member = memberOptional.get();
        String token = jwtTokenProvider.createToken(kakaoId.toString(), member.getRoles()); //사용자 식별용 고유값인 kakaoId와 권한단계인 role을 담은 토큰 생성
//        System.out.println("token : "+token);

        MemberResponse.LoginResponse loginResponse = new MemberResponse.LoginResponse(
                token,
                member.getNickname(),
                member.getSido(),
                member.getGugun()
        );

        return loginResponse;
    }

    public Long addMember(HttpServletRequest httpServletRequest, MemberRequest.SignUpRequest request){
        log.info("[SERVICE] addMember");
        String kakaoToken = httpServletRequest.getHeader("Authorization");
        HashMap<String, Object> resultMap = getKakaoInfo(kakaoToken, "signUp");

        Long kakaoId = (Long) resultMap.get("id");

        Optional<Member> findMember = memberRepository.findByKakaoId(kakaoId);
        if(!findMember.isEmpty()){
            throw new CustomException(HttpStatus.CONFLICT, "Already exist member by this kakao id.");
        }
//        Optional<Member> findMember = memberRepository.findByKakaoId(kakaoId)
//                .orElseThrow(() -> new RuntimeException("Member connected this kakao id is not exist. Please sign up."));
//        if(findMember != null){
//            throw new RuntimeException("Already exist member by this kakao id.");
//        }

        //카카오로부터 받아온 정보
        String nickname = resultMap.get("nickname").toString();
        String email = resultMap.get("email").toString();

        //request body로 받아온 정보
        String sido = request.getSido();
        String gugun = request.getGugun();
        String loginType = request.getLoginType();

        Member newMember = Member.builder()
                .kakaoId(kakaoId)
                .nickname(nickname)
                .email(email)
                .sido(sido)
                .gugun(gugun)
                .loginType(loginType)
                .roles(Collections.singletonList("ROLE_USER")) //회원가입 시 role을 USER로 설정 //TODO : ?흠 이게 DB에 저장되네..
                .build();

        return memberRepository.save(newMember).getMemberIdx(); //save 하고 바로 Idx값 구하기
    }

    public String refreshToken(HttpServletRequest servletRequest){
        Long kakaoId = jwtTokenProvider.getUserPKByServlet(servletRequest);

        Optional<Member> memberOptional = memberRepository.findByKakaoId(kakaoId);
        if(memberOptional.isEmpty()){
            System.out.println("No member, kakao id : "+ kakaoId);
            throw new RestApiException(MemberErrorCode.NOT_EXIST_KAKAO_MEMBER);
        }

        return jwtTokenProvider.createToken(kakaoId.toString(), memberOptional.get().getRoles());

    }


    private HashMap<String, Object> getKakaoInfo(String kakaoToken, String type) {
        log.info("[METHOD] getKakaoInfo");

        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        String requestUrl = "https://kapi.kakao.com/v2/user/me";

        //accessToken을 사용하여 사용자 정보 조회하기
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST"); //GET또는 POST로 가능
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + kakaoToken); //카카오 문서대로 헤더 달기(난 어드민 키 말고 액세스 토큰 사용!)
            //TODO : 민영이 폰 바꿔서 회원가입 안되던 에러... 여기쯤서 문제됐던 걸지도
            
            
            //응답 결과 200이면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);
            if(responseCode == 401){
                throw new CustomException(HttpStatus.UNAUTHORIZED, "Kakao token is invalid");
            }

            //응답 결과 json 받아오기
            BufferedReader buf = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            StringBuilder all = new StringBuilder(); //몰라 인텔리제이가 String에 +=보다 이게 더 좋대..

            while ((line = buf.readLine()) != null) { //반복문을 돌때마다 line=buf.readLine()을 실행하고 현재 줄line이 비어있지(null) 않을 때까지 읽음
                all.append(line);
            }
            System.out.println("responseBody : " + all);

            //읽어온 String 형태 result를 Gson 라이브러리로 json으로 파싱
            JsonElement element = JsonParser.parseString(all.toString());

            //needs_agreement 항목들은 false일 경우 동의가 완료된 거고, 가져올 수 있음. true면 아직 동의가 필요한 상태라는 뜻
            //회원번호
            Long id = element.getAsJsonObject().get("id").getAsLong();
            resultMap.put("id", id);

            if(type.equals("signUp")){
                //닉네임, 이메일
                JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
                boolean nicknameAgree = kakaoAccount.get("profile_nickname_needs_agreement").getAsBoolean();
                String nickname = "";
                if(!nicknameAgree){
                    nickname = kakaoAccount.get("profile").getAsJsonObject().get("nickname").getAsString();
                    resultMap.put("nickname", nickname);
                }
                boolean emailAgree = kakaoAccount.get("email_needs_agreement").getAsBoolean();
                String email = "";
                if(!emailAgree){
                    email = kakaoAccount.get("email").getAsString();
                    resultMap.put("email", email);
                }

//                System.out.println("nicknameAgree : "+nicknameAgree);
//                System.out.println("nickname : "+nickname);
//                System.out.println("emailAgree : "+emailAgree);
//                System.out.println("email : "+email);
            }

//            System.out.println("id : "+id);
            buf.close();

        } catch (IOException e) { //URL 에서 던지는 에러핸들링
            throw new RuntimeException(e);
        }
        return resultMap;
    }


}
