package com.example.dongnaegoyangserver2022.global.config.jwt;

import com.example.dongnaegoyangserver2022.global.config.exception.RestApiException;
import com.example.dongnaegoyangserver2022.global.config.exception.error.MemberErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private String secretKey = "jhGoyang";

    //토큰 유효시간 : 7일
    private final long tokenValidTime = 7 * 24 * 60 * 60 * 1000L;

    private final UserDetailsService userDetailsService;

    //객체 초기화. secretKey를 Base64로 인코딩함
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    //JWT 토큰 생성
    public String createToken(String userPK, List<String> roles) { //TODO : 난 userPK가 아니지 않을까?
        Claims claims = Jwts.claims().setSubject(userPK); //JWT payload부분에 저장되는 정보 단위 claim
        claims.put("roles", roles); //claim에 원하는 정보 넣음
        Date now = new Date(); //현재 일시
        return Jwts.builder()
                .setClaims(claims) //토큰에 정보를 담은 claim 저장
                .setIssuedAt(now) //토큰 발행 시간 저장
                .setExpiration(new Date(now.getTime() + tokenValidTime)) //토큰 만료 시각 설정
                .signWith(SignatureAlgorithm.HS256, secretKey) //암호화에 사용할 알고리즘과 JWT signiture부분에 들어갈 secretKey
                .compact();
    }


    public Long getUserPKByServlet(HttpServletRequest httpServletRequest) throws RestApiException {
        // TODO : 이거 사용하는 곳들 수정하기.. memberRepository로부터의 Member 조회는 MemberService에서만 하도록 해야하는데 와전 깊숙한 도메인영역까지 끌고 들어간 듯
        //  이거 대신 MemberService의 getMemberByHeader사용하도록 수정하기
        //  -> 아닌가......이게 맞네 아 비회원 지대 헷갈려....... 토큰kakaoId랑 Member를 따로 생각하면 괜찮은 것 같기도 하고...
        //  이것도 쓰도록 하는(kakaoId를 깊숙한 곳에서 직접 사용) 게 맞는 것 같아... 이게 꼭 select쿼리를 한번 더 보내야하는 상황이 아니라면 이게 효울적이지
        //  개발자는 유연하게 사고한다!!!!! 정답은 없다!
        String token = "";
        try {
            token = resolveToken(httpServletRequest);
        }
        catch (RestApiException apiException){
            if(apiException.getErrorCode() == MemberErrorCode.GUEST_USER){
                //비회원 사용자 처리
                return null;
            } else {
                throw apiException;
            }
        }
        return getUserPK(token);
    }

    public boolean validateTokenByServlet(HttpServletRequest httpServletRequest) throws RestApiException {
        String token = "";
        try {
            token = resolveToken(httpServletRequest);
        }
        catch (RestApiException apiException){
            if(apiException.getErrorCode() == MemberErrorCode.GUEST_USER){
                //비회원 사용자 처리
                return false;
            } else {
                throw apiException;
            }
        }
        return validateToken(token);
    }

    //토큰에서 회원 정보 추출
    private Long getUserPK(String token) {
        long userPK;
        try {
            String userPKString = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject(); //setSubject했던 값 가져오기
            userPK = Long.parseLong(userPKString);
        } catch (Exception e) {
            throw new RestApiException(MemberErrorCode.INVALID_TOKEN); //토큰에서 회원 정보를 확인할 수 없을 때 throw
        }
        return userPK;
    }

    //토큰에서 인증정보 조회
//    public Authentication getAuthentication(String token) {
    public Authentication getAuthenticationByServlet(HttpServletRequest httpServletRequest) {
        String token = resolveToken(httpServletRequest);
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPK(token).toString()); //토큰에서 회원정보 추출하는 getUserOK메서드 사용
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    //요청 Header에서 토큰 가져옴
    private String resolveToken(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("X-AUTH-TOKEN");
        if(token == null || token.isEmpty()){
            //토큰이 비어있으면 비회원 사용으로 간주함
            throw new RestApiException(MemberErrorCode.GUEST_USER);
        }
        return token;
    }

    //토큰의 비암호화와 만료일자로 유효 여부 확인 - true/false
    private boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date()); //만료일시 지났는지로 유효여부 판단 (만료일시가 현재일시보다 전이면 false)
        } catch (Exception e){
            return false; //유효하지 않음 (setSigningKey 에서 파싱 시 에러인가봄)
        }
    }

}
