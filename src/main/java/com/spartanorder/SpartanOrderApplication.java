package com.spartanorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpartanOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpartanOrderApplication.class, args);
    }

}
