package com.realestate.service.user.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import com.realestate.service.config.TestConfig;
import com.realestate.service.user.constant.Role;
import com.realestate.service.user.constant.Status;
import com.realestate.service.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DisplayName("UserRepository 테스트")
@ActiveProfiles("test")
@Import(TestConfig.class)
@DataJpaTest
@Slf4j
public class UserRepositoryTest {

  @Autowired
  UserRepository userRepository;

  User givenUser = null;
  final String givenEmail = "jake@gmail.com";
  final String givenPassword = "helloworld2022";
  final String givenNickName = "bestdeveloper";
  final String givenEmail2 = "jake222@gmail.com";
  final String givenPassword2 = "helloworld22022";
  final String givenNickName2 = "bestdeveloper222";

  @Nested
  @DisplayName("save 메서드는")
  class DescribeOf_save {


    @Nested
    @DisplayName("사용자 정보가 주어질 경우")
    class ContextWith_createUser {

      @Test
      @DisplayName("사용자 정보를 저장한 후 저장된 정보를 반환한다.")
      void it_returns() {

        //given
        User givenUser = User.createUser(givenEmail, givenPassword, givenNickName, Status.ACTIVE, Role.NORMAL);

        //when
        User savedUser = userRepository.save(givenUser);

        log.info(savedUser.getEmail());
        log.info(savedUser.getPassword());

        //then
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo(givenEmail);
        assertThat(savedUser.getPassword()).isEqualTo(givenPassword);
        assertThat(savedUser.getNickName()).isEqualTo(givenNickName);
        assertThat(savedUser.getStatus()).isEqualTo(Status.ACTIVE);
        assertThat(savedUser.getRole()).isEqualTo(Role.NORMAL);

      }

    }


  }


  @BeforeEach
  void setUp() {

    User givenUser2 = User.createUser(
        givenEmail2,
        givenPassword2,
        givenNickName2,
        Status.ACTIVE,
        Role.NORMAL);

    userRepository.save(givenUser2);
  }


  @Nested
  @DisplayName("사용자를 조회할 때")
  class findUserBy {

    @Nested
    @DisplayName("이메일을 사용하는 경우")
    class email {

      @Test
      @DisplayName("사용자 정보를 반환한다.")
      void it_returns() {

        Optional<User> getUser = userRepository.findUserByEmail(givenEmail2);

        assertThat(getUser).isNotNull();
        assertThat(getUser.get().getEmail()).isEqualTo(givenEmail2);
        assertThat(getUser.get().getPassword()).isEqualTo(givenPassword2);

      }


    }


  }

}
