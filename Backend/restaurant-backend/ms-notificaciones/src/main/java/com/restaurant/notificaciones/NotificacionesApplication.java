package com.restaurant.notificaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = "com.restaurant")
@EnableDiscoveryClient
public class NotificacionesApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificacionesApplication.class, args);
    }
}
