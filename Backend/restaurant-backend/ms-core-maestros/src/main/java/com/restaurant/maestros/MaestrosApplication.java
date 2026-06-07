package com.restaurant.maestros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = "com.restaurant")
@EnableDiscoveryClient
public class MaestrosApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaestrosApplication.class, args);
    }
}
