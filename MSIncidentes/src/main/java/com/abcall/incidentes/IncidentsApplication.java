package com.abcall.incidentes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class IncidentsApplication {

    public static void main(String[] args) {
        SpringApplication.run(IncidentsApplication.class, args);
    }

}
