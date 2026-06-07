package com.restaurant.ventas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.restaurant")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.restaurant.ventas.client")
public class VentasApplication {

    public static void main(String[] args) {
        SpringApplication.run(VentasApplication.class, args);
    }
}
