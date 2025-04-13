package com.abcall.incidentes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class IncidentesApplication {

    public static void main(String[] args) {
        SpringApplication.run(IncidentesApplication.class, args);
    }

}
