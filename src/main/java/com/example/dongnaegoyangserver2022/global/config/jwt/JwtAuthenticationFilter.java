package com.example.dongnaegoyangserver2022.global.config.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        //Header에서 토큰 받아오기
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);

        //토큰 유효 여부 확인
        if(token != null && jwtTokenProvider.validateToken(token)) {
            //유효함.
            // 토큰에서 유저정보(Authentication) 받아옴
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            // SecurityContext에 Authentication 객체를 저장함
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
