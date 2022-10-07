/*
package com.example.dongnaegoyangserver2022.cat;

import com.example.dongnaegoyangserver2022.domain.cat.api.CatController;
import com.example.dongnaegoyangserver2022.global.config.jwt.JwtTokenProvider;
//import com.example.dongnaegoyangserver2022.global.config.security.WebSecurityConfig;
import com.example.dongnaegoyangserver2022.global.security.user.CustomUserDetailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; // TODO : 왜 자동으로 import가 안될까

//JUnit4 사용

//@WebAppConfiguration //스프링 프레임워크에서 웹 버전의 애플리케이션 컨텍스트(빈의 생성과 관계설정 등 제어를 담당하는IoC 컨테이너)를 생성할 때 사용한다.
//@ExtendWith(SpringExtension.class) //JUnit5 의 RunWith
@RunWith(SpringRunner.class) //JUnit과 스프릥 부트 사이의 연결자 역할. 테스트를 진행할 때 JUnit에 내장된 실행자 외에 다른 실행자(SpringRunner)를 실행시킨다.
@WebMvcTest(controllers = CatController.class) //@Controller, @ControllerAdvice 를 테스트해볼 수 있는 어노테이션. @Service, @Component, @Repository는 불가
//JWT토큰 관련 코드들 때문에 위 @WebMvcTest 어노테이션으로는 테스트가 불가한 것 같다. JwtTokenProvider가 @Component가 달려있고 거기서 에러가 난다.
// 다른 걸로 시도
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //JPA까지 테스트 시 사용
//@Import(value = {WebSecurityConfig.class, JwtTokenProvider.class, CustomUserDetailService.class})
public class CatControllerTest {

//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private TestRestTemplate restTemplate; //JPA까지 테스트 시 사용

    @Autowired
    private MockMvc mvc;

    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

//        String url = "http://localhost:" + port + "/v1/cats/hello";

//        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.GET);

//        assertThat()

        mvc.perform(get("/v1/cats/hello-string"))
//                .andExpect(status().isOk())
//                .andExpect(content().string(hello));
                .andExpect(status().is4xxClientError());
    }
}
*/
