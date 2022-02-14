package com.realestate.service.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.realestate.service.user.constant.Role;
import com.realestate.service.user.constant.Status;
import com.realestate.service.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserSignupDto {

  @NotBlank(message = "이메일 입력은 필수입니다.")
  @Email(message = "이메일 형식이 올바르지 않습니다.")
  private String email;

  @NotBlank(message = "비밀번호 입력은 필수입니다.")
  @Length(min = 8, max = 20, message = "8~20자리 비밀번호를 입력해주세요.")
  private String password;

  private String nickName;
  private String status;
  private String role;


  /**.
   * 생성자 : UserSignupDto를 생성하기 위한 빌더함수
   * */
  public UserSignupDto(String email, String password, String nickName) {
    this.email = email;
    this.password = password;
    this.nickName = nickName;
  }



  /**.
   * 회원빌드 : User 엔티티를 생성하기 위한 빌더함수
   * */
  public User toUserEntity(UserSignupDto userSignupDto) {
    User user = User.builder()
                    .email(userSignupDto.getEmail())
                    .password(userSignupDto.getPassword())
                    .nickName(userSignupDto.getNickName())
                    .status(Status.valueOf(userSignupDto.getStatus()))
                    .role(Role.valueOf(userSignupDto.getRole()))
                    .build();

    return user;
  }


}
