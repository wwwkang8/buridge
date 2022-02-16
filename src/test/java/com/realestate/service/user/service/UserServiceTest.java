package com.realestate.service.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

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
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@DisplayName("UserService 테스트")
@ExtendWith(MockitoExtension.class)
@Slf4j
public class UserServiceTest {

  /** @ExtendWith : JUnit5와 Mockito를 연동하기 위한 방법
   *  Mockito : 테스트를 위해서 가짜 객체를 생성하는 테스팅 프레임워크 */

  /**
   * 참고 자료 : https://johngrib.github.io/wiki/junit5-nested/
   * 테스트 작성 이해에 도움이 되는 링크 : https://kim-solshar.tistory.com/60
   * */

  // 실제 테스트 대상이 되는 객체이기 떄문에 BeforeEach에서 생성자로 생성.
  private UserService userService;

  @Mock
  private UserRepository userRepository; // userRepository 테스트가 아니기 때문에 Mock 객체로 생성

  private PasswordEncoder passwordEncoder; // PasswordEncoder 테스트가 아니기 떄문에 Mock 객체로 생성


  static final String givenEmail = "test@gmail.com";
  static final String givenPassword = "helloworldTest";
  static final String givenNickName = "swisswatch";


  @BeforeEach
  void setup() {

    // UserService, PasswordEncoder 객체는 실제 객체로 사용
    passwordEncoder = new BCryptPasswordEncoder();
    userService = new UserService(userRepository, passwordEncoder);


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

        // given : userRepository.save 시 무조건 아래 user 엔티티가 반환되도록 정의.
        User user = User.createUser(givenEmail, givenPassword, givenNickName, Status.ACTIVE, Role.NORMAL);
        given(userRepository.save(any())).willReturn(user);

        // when : userService.signup 함수 호출 시 위에서 정의한 user가 반환된다.
        UserSignupDto userSignupDto = new UserSignupDto(givenEmail, givenPassword, givenNickName);
        User savedUser = userService.signup(userSignupDto);


        // then : savedUser와 미리 정의한 반환된 user를 비교 검증
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo(givenEmail);
        assertThat(savedUser.getNickName()).isEqualTo(givenNickName);
        assertThat(savedUser.getStatus()).isEqualTo(Status.ACTIVE);
        assertThat(savedUser.getRole()).isEqualTo(Role.NORMAL);

      }


    }

  }


  @Nested
  @DisplayName("비밀번호 변경하는 경우")
  class changePassword {

      @Test
      @DisplayName("인증코드 6자리 발급한다.")
      void getSecretCode() {

        // given : findUserByEmail 호출 시 무조건 user가 반환되게끔 정의함.
        User user = User.createUser(givenEmail, givenPassword, givenNickName, Status.ACTIVE, Role.NORMAL);
        given(userRepository.findUserByEmail(any())).willReturn(java.util.Optional.ofNullable(user));

        // when : user가 반환되었기 떄문에 "회원정보가 없습니다" 오류가 발생하지 않음. 그리고 secretCode 6개 반환
        UserEmailDto userEmailDto = new UserEmailDto(givenEmail);
        int secretCode = userService.generateSecretCode(userEmailDto);
        String stringSecretCode = secretCode+"";

        log.info("String secretcode : " + secretCode);

        // then : 발행한 비밀번호 인증 secret 코드를 검증.
        assertThat(stringSecretCode.length()).isEqualTo(6);
        assertThat(secretCode).isGreaterThanOrEqualTo(0);
        assertThat(secretCode).isLessThanOrEqualTo(999999);
      }

  }


}
