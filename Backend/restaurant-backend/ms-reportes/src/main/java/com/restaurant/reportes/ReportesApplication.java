package com.restaurant.reportes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.restaurant")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.restaurant.reportes.client")
public class ReportesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReportesApplication.class, args);
    }
}
