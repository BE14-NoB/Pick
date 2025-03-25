package com.nob.pick.pickconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class PickConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PickConfigServerApplication.class, args);
    }

}
