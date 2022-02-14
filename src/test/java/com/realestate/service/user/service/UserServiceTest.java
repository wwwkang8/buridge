package com.realestate.service.user.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import com.realestate.service.user.constant.Role;
import com.realestate.service.user.constant.Status;
import com.realestate.service.user.dto.UserEmailDto;
import com.realestate.service.user.dto.UserSignupDto;
import com.realestate.service.user.entity.User;
import com.realestate.service.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;


@DisplayName("UserService 테스트")
@ExtendWith(MockitoExtension.class)
@Slf4j
public class UserServiceTest {

  /** @ExtendWith : JUnit5와 Mockito를 연동하기 위한 방법
   *  Mockito : 테스트를 위해서 가짜 객체를 생성하는 테스팅 프레임워크 */

  /**
   * 참고 자료 : https://johngrib.github.io/wiki/junit5-nested/
   * */

  @InjectMocks
  UserService userService;

  @Mock
  UserRepository userRepository;

  @Mock
  PasswordEncoder passwordEncoder;


  final String givenEmail = "test@gmail.com";
  final String givenPassword = "helloworldTest";
  final String givenNickName = "swisswatch";


  @BeforeEach
  void setup() {

    // given
    UserSignupDto userSignupDto = new UserSignupDto(givenEmail, givenPassword, givenNickName);

    // when
    userService.signup(userSignupDto);

  }


  @Nested
  @DisplayName("사용자가 회원가입을 요청하면")
  class DescribeOf_signup {

    @Nested
    @DisplayName("이메일, 비밀번호, 닉네임을 입력받아서")
    class getUserInfo {

      @Test
      @DisplayName("회원정보를 저장한다.")
      void saveUser() {

        // given
//        UserSignupDto userSignupDto = new UserSignupDto(givenEmail, givenPassword, givenNickName);
//
//        // when
//        userService.signup(userSignupDto);


        // then
        Optional<User> savedUser = userRepository.findUserByEmail(givenEmail);

        log.info("확인용 : " + savedUser);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.get().getEmail()).isEqualTo(givenEmail);
        assertThat(savedUser.get().getPassword()).isNotEqualTo(givenPassword); // 암호화 되었기 때문에 평문과 다름
        assertThat(savedUser.get().getNickName()).isEqualTo(givenNickName);
        assertThat(savedUser.get().getStatus()).isEqualTo(Status.ACTIVE);
        assertThat(savedUser.get().getRole()).isEqualTo(Role.NORMAL);

      }


    }

  }


  @Nested
  @DisplayName("비밀번호 변경하는 경우")
  class changePassword {

      @Test
      @DisplayName("인증코드 6자리 발급한다.")
      void getSecretCode() {

        UserEmailDto userEmailDto = new UserEmailDto(givenEmail);

        int secretCode = userService.generateSecretCode(userEmailDto);

        assertThat(Math.log10(secretCode)+1 ).isEqualTo(6);
        assertThat(secretCode).isGreaterThanOrEqualTo(0);
        assertThat(secretCode).isLessThanOrEqualTo(999999);
      }



      @Test
      @DisplayName("발급한 인증코드 이메일 송신")
      void sendEmail() {

        UserEmailDto userEmailDto = new UserEmailDto(givenEmail);

        int secretCode = userService.generateSecretCode(userEmailDto);

        userService.sendEmail(userEmailDto, secretCode);
      }


  }


}
