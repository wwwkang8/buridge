package com.realestate.service.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DatabaseConfig {

  public static final String DATASOURCE_BEAN_ID = "dataSource";

  @Bean
  public JdbcTemplate jdbcTemplate(@Qualifier(DATASOURCE_BEAN_ID) DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }
}
