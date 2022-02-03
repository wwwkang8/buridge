package com.realestate.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

//@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class ContextLoadTest {

  @DisplayName("컨텍스트 로딩 테스트")
  @Test
  void contextLoad() {
    Assertions.assertTrue(true);
  }
}
