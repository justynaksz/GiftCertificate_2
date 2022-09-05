package com.epam.esm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EntityScan("com.epam.esm")
public class GiftCertificateApplication {
    public static void main(String[] args) {
        SpringApplication.run(GiftCertificateApplication.class, args);
    }
}
