package com.sparta.moit;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@OpenAPIDefinition(servers = {@Server(url = "https://api.moit.app", description = "Default Server URL")})
@EnableJpaAuditing
@SpringBootApplication
@EnableScheduling
public class MoitApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoitApplication.class, args);
//        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }
}
