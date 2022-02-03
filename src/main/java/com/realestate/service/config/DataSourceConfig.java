package com.realestate.service.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfig {

  @Primary
  @Bean(DatabaseConfig.DATASOURCE_BEAN_ID)
  @ConfigurationProperties("spring.datasource.hikari")
  DataSource dataSource() {
    return DataSourceBuilder.create().build();
  }

}
