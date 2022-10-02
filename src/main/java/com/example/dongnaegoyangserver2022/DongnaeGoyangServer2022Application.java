package com.example.dongnaegoyangserver2022;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //JPA Auditing 활성화
@SpringBootApplication
public class DongnaeGoyangServer2022Application {

    public static void main(String[] args) {
        SpringApplication.run(DongnaeGoyangServer2022Application.class, args);
    }

}
