package com.example.dongnaegoyangserver2022.global.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("v1-definition") // 그룹명 설정 (Swagger API 명세서에서 이 이름으로 그룹 선택)
                .pathsToMatch("/v1/**") // http:localhost:8080/v1/ 로 시작하는 주소를 다 매칭해서 Swagger API 명세서에 보여준다.
                .build();
    }

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("동네고영희 API")
                        .description("동네고영희 서비스의 API 명세서입니다.")
                        .version("v0.0.1"));
                        // 여기에 적은 내용들이 Swagger API 명세서에 들어왔을 때 나타난다.
    }
}
