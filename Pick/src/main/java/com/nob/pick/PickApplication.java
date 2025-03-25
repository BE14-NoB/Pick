package com.nob.pick;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PickApplication {

    public static void main(String[] args) {
        SpringApplication.run(PickApplication.class, args);
    }

}
