package com.realestate.service.user.service;

import java.io.IOException;
import java.util.Random;

import com.realestate.service.user.constant.Role;
import com.realestate.service.user.constant.Status;
import com.realestate.service.user.dto.UserSignupDto;
import com.realestate.service.user.entity.User;
import com.realestate.service.user.repository.UserRepository;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Value("${spring.sendgrid.api-key}")
  String sendGridApiKey;


  /**.
   * 회원가입 : 이메일, 비밀번호를 입력하면 회원가입
   * @author Kang Jeong Ho
   * @version 1.0
   * */
  @Transactional
  public void signup(UserSignupDto userSignupDto) {

    // 비밀번호 암호화 처리
    String encodedPassword = passwordEncoder.encode(userSignupDto.getPassword());


    //User 객체 빌드하기
    User user = User.builder()
        .email(userSignupDto.getEmail())
        .password(encodedPassword)
        .nickName(userSignupDto.getNickName())
        .status(Status.valueOf(userSignupDto.getStatus()))
        .role(Role.valueOf(userSignupDto.getRole()))
        .build();


    userRepository.save(user);
  }

  /**.
   * 인증번호 전송 : 이메일 주소로 인증번호 전송.
   * @author Kang Jeong Ho
   * @version 1.0
   * */
  public void sendValidationCode(UserSignupDto userSignupDto) {

    /** 인증번호 6자리 발행 */
    int secretCode = generateSecretCode(userSignupDto);

    /** 인증번호 이메일 전송 */
    sendEmail(userSignupDto, secretCode);

  }

  /**.
   * 인증코드 발행 : 비밀번호 변경을 위한 인증코드 발행
   * @author Kang Jeong Ho
   * @version 1.0
   * */
  public int generateSecretCode(UserSignupDto userSignupDto) {

    /** 6자리 난수 발생 */
    Random rnd = new Random();
    int secretCode = rnd.nextInt(999999);

    /** 이메일로 사용자 조회 */
    User user = userRepository.findUserByEmail(userSignupDto.getEmail());

    /** 사용자의 인증코드 업데이트 */
    user.updateUserValidationCode(secretCode);

    return secretCode;
  }

  /**.
   * 이메일 전송 : 사용자에게 비밀번호 인증코드 이메일 전송
   * @author Kang Jeong Ho
   * @version 1.0
   * */
  public void sendEmail(UserSignupDto userSignupDto, int secretCode) {

    /** 이메일 객체 생성 : 송신자, 수신자, 제목, 이메일 내용 */
    Email fromEmail = new Email("sendgrid API에 등록된 이메일 주소 --> 상수값으로 뺼 예정");
    Email toEmail = new Email(userSignupDto.getEmail());
    String subject = "@@@@@ Buridge password validation code @@@@@@";
    String txt = "Validation code is " + secretCode;
    Content content = new Content("text/plain", txt);
    Mail mail = new Mail(fromEmail, subject, toEmail, content);

    /** 이메일 전송 */
    SendGrid sg = new SendGrid(sendGridApiKey);
    Request request = new Request();
    try {
      request.setMethod(Method.POST);
      request.setEndpoint("mail/send");
      request.setBody(mail.build());
      Response response = sg.api(request);
      System.out.println(response.getStatusCode());
      System.out.println(response.getBody());
      System.out.println(response.getHeaders());

      System.out.println(userSignupDto.getEmail() + " 비밀번호 인증코드 전송완료");
    } catch (IOException e) {
      System.out.println("SendGrid IO Exception");
      e.printStackTrace();
    }


  }



}
