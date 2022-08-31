package com.example.dongnaegoyangserver2022.config.security;

import com.example.dongnaegoyangserver2022.config.jwt.JwtAuthenticationFilter;
import com.example.dongnaegoyangserver2022.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter { //TODO : deprecated 대응

    private final JwtTokenProvider jwtTokenProvider;

    //암호화에 필요한 PasswordEncoder 를 Bean등록함
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    //AuthenticationManager 를 Bean등록함
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable() //rest api 만을 고려하여 기본 설정을 해제함
                .csrf().disable() //csrf 보안 토큰 disable // TODO : ??
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // STATELESS : 세션이 아닌 토큰 기반 인증을 사용하므로 세션을 사용하지 않음
                .and()
                .authorizeRequests() //요청에 대한 사용권한 체크
//                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/members/**").hasRole("USER")
                .antMatchers("/**").permitAll() //그 외 요청들은 누구나 접근 허용
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);
                //JwtAuthenticationFilter 를 UsernamePasswordAuthenticationFilter 전에 넣음
    }
}
