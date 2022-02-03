package com.realestate.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class RealEstateServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(RealEstateServiceApplication.class, args);
  }

}
