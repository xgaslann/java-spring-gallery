package com.xgaslan.starter;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("com.xgaslan.data.entities")
@EnableJpaRepositories(basePackages = "com.xgaslan.repositories")
@ComponentScan(basePackages = "com.xgaslan")
@SpringBootApplication(scanBasePackages = "com.xgaslan")
public class GalleryApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().load();

        System.out.println("DB_URL = " + dotenv.get("DB_URL"));
        System.out.println("DB_USERNAME = " + dotenv.get("DB_USERNAME"));
        System.out.println("DB_PASSWORD = " + dotenv.get("DB_PASSWORD"));
        System.out.println("JWT_SECRET from dotenv: " + dotenv.get("JWT_SECRET_KEY"));

        dotenv.entries().forEach(entry ->
                System.setProperty(entry.getKey(), entry.getValue())
        );

        SpringApplication.run(GalleryApplication.class, args);
    }

}
