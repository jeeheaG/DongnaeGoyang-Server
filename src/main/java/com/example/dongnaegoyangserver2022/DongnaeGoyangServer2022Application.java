package com.example.dongnaegoyangserver2022;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;

@EnableJpaAuditing //JPA Auditing 활성화
@SpringBootApplication
public class DongnaeGoyangServer2022Application {

    @PostConstruct
    public void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
//        System.out.println("현재 시각 : "+ new Date());
    }

    public static void main(String[] args) {
        SpringApplication.run(DongnaeGoyangServer2022Application.class, args);
    }

}
