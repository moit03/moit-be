package com.sparta.moit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MoitApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoitApplication.class, args);
    }

}
