package com.nob.pick.pickgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PickGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(PickGatewayApplication.class, args);
    }

}
