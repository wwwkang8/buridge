package com.realestate.service.web.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import com.realestate.service.config.WebSecurityConfig;
import com.realestate.service.user.entity.User;
import com.realestate.service.user.exception.UserNotFoundException;
import com.realestate.service.user.jwt.JwtAuthenticationEntryPoint;
import com.realestate.service.user.jwt.JwtRequestFilter;
import com.realestate.service.user.jwt.JwtTokenUtil;
import com.realestate.service.user.jwt.JwtUserDetailService;
import com.realestate.service.user.service.PasswordEncoderService;
import com.realestate.service.user.service.UserService;
import com.realestate.service.web.user.response.SignupUserResponse;
import com.realestate.service.web.user.response.UserInfoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@DisplayName("사용자 정보")
@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(UserRestController.class)
@MockBean(JpaMetamodelMappingContext.class)
@ActiveProfiles("test")
@AutoConfigureRestDocs
public class UserRestDoc {

  /**
   * 테스트 작성시 참고한 링크 : https://kim-solshar.tistory.com/60
   * */

  final UserMockHelper userMockHelper = new UserMockHelper();

  private MockMvc mockMvc;

  @MockBean
  UserService userService;

  @MockBean
  WebSecurityConfig webSecurityConfig;

  @MockBean
  JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

  @MockBean
  JwtTokenUtil jwtTokenUtil;

  @MockBean
  JwtUserDetailService jwtUserDetailService;

  @MockBean
  JwtRequestFilter jwtRequestFilter;

  @MockBean
  AuthenticationManager authenticationManager;

  @MockBean
  PasswordEncoderService passwordEncoderService;

  @MockBean
  WebSecurityConfiguration webSecurityConfiguration;



  @BeforeEach
  void setup(WebApplicationContext webApplicationContext
            , RestDocumentationContextProvider restDocumentationContextProvider) {

    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext) //Mock을 사용하기 위한 설정
                                  .apply(documentationConfiguration(restDocumentationContextProvider))// 문서화 하는 설정
                                  .alwaysDo(print())
                                  .addFilters(new CharacterEncodingFilter("UTF-8", true))
                                  .build();





  }


  @Test
  @DisplayName("사용자 등록")
  void signup() throws Exception {

    /***
     * 용도 : static 함수를 모킹하기 위해서는 Mockito.mockStatic 함수가 필요함.
     * 참고링크 : https://asolntsev.github.io/en/2020/07/11/mockito-static-methods/
     */
    Mockito.mockStatic(SignupUserResponse.class);

    // User 엔티티 생성 : signup 함수를 수행한 후 반환되는 User 엔티티를 표현
    var user = userMockHelper.createUser();

    // UserSignupDto 생성 : signup 함수를 수행하기 위한 Mock 객체 생성
    var userSignupDto = userMockHelper.createUserSignupDto();

    // SignupResponse 생성 : Controller에서 응답 시 반환하는 객체를 생성.
    var signupResponse = userMockHelper.createSignupUserResponse();

    /**
     * given
     * 용도 : signup 함수 호출 후 User 엔티티가 save 되었을 때 반환되는 User 엔티티를 정의.
     * */
    given(userService.signup(userSignupDto)).willReturn(user);

    // 용도 : 컨트롤러에서 응답시, save된 user 엔티티의 정보로 응답이 나가는 것을 정의.
    given(SignupUserResponse.toSignupUserResponse(user)).willReturn(signupResponse);


    /**
     * when
     * post : /api/users/signup/ URL 호출
     * content : JSON으로 된 입력 내용 받는다.
     * contentType : 입력값의 타입
     * */
    ResultActions resultActions = mockMvc.perform(
        post("/api/users/signup/")
            .content(userMockHelper.getSignupRequest())
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
    );

    /**
     * then
     * RestDoc 참고링크 : https://techblog.woowahan.com/2597/
     * 참고링크 2 : https://jaehun2841.github.io/2019/08/04/2019-08-04-spring-rest-docs/#spring-rest-docs-architecture
     * */
    resultActions.andExpect(status().isOk())
                 .andDo(document("user/signup",
                     preprocessRequest(prettyPrint()),
                     preprocessResponse(prettyPrint()),
                     requestFields(
                         fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                         fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                         fieldWithPath("nickName").type(JsonFieldType.STRING).description("닉네임"),
                         fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                         fieldWithPath("role").type(JsonFieldType.STRING).description("권한")
                     ),
                     responseFields(
                         fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
                         fieldWithPath("message").type(JsonFieldType.NULL).description("메시지"),
                         fieldWithPath("data").type(JsonFieldType.NULL).description("데이터")
                      )
                     )
                 );
  }

  @Test
  @DisplayName("JWT 토큰 발행")
  void authenticate() throws Exception {
    // User 엔티티 생성 : signup 함수를 수행한 후 반환되는 User 엔티티를 표현
    var user = userMockHelper.createUser();

    // JwtRequest 생성 : 이메일, 비밀번호로 JwtRequest 생성
    var jwtRequest = userMockHelper.createJwtRequest();

    // JwtResponse 생성 : 발행한 토큰 응답 객체
    var jwtResponse = userMockHelper.createJwtResponse();

    // 생성된 토큰 : Mock으로 생성된 토큰
    String givenToken = jwtResponse.getJwtToken();

    // UserDetails 생성 : JwtRequest를 사용하여 Mock UserDetails 생성
    var userDetails = jwtUserDetailService.loadUserByUsername(jwtRequest.getEmail());

    // jwtUserDetailService로 loadUserByUsername을 호출하면 이전에 생성된 userDetails를 리턴
    given(jwtUserDetailService.loadUserByUsername(jwtRequest.getEmail())).willReturn(userDetails);

    // jwtTokenUtil로 generateToken 호출하면 Mock으로 생성한 토큰 리턴
    given(jwtTokenUtil.generateToken(userDetails)).willReturn(givenToken);

    /**
     * when
     * post : /api/users/authenticate/ URL 호출
     * content : JSON으로 된 입력 내용 받는다.
     * contentType : 입력값의 타입
     * */
    ResultActions resultActions = mockMvc.perform(
        post("/api/users/authenticate/")
            .content(userMockHelper.getJwtRequest())
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
    );

    /**
     * then
     * RestDoc 참고링크 : https://techblog.woowahan.com/2597/
     * 참고링크 2 : https://jaehun2841.github.io/2019/08/04/2019-08-04-spring-rest-docs/#spring-rest-docs-architecture
     * */
    resultActions.andExpect(status().isOk())
        .andDo(document("user/authenticate",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            requestFields(
                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
            ),
            responseFields(
                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
                fieldWithPath("message").type(JsonFieldType.NULL).description("메시지"),
                fieldWithPath("data.jwtToken").type(JsonFieldType.STRING).description("데이터")
            )
            )
        );
  }

  @Test
  @DisplayName("비밀번호 변경")
  void changePassword() throws Exception {

//    // User 엔티티 생성
//    var user = userMockHelper.createUser();
//
//    // UserInfoDto 생성
//    var userInfoDto = userMockHelper.createUserInfoDto(user);
//
//    // UserInfoRequest 생성
//    var userInfoRequest = userMockHelper.createUserInfoRequest();
//
//    // UserInfoResponse 생성
//    var userInfoResponse = new UserInfoResponse(user.getEmail(), user.getEmail());
//
//
//    //given
//    given(userService.findUserByEmail(userInfoDto.getEmail())).willReturn(user);
//    given(userService.generateSecretCode(userInfoDto)).willReturn(123123);
//    //given(userService.changePassword(userInfoDto)).willReturn(user);
//
//
//
//    /**
//     * when
//     * post : /api/users/password/change URL 호출
//     * content : JSON으로 된 입력 내용 받는다.
//     * contentType : 입력값의 타입
//     * */
//    ResultActions resultActions = mockMvc.perform(
//        post("/api/users/password/change/")
//            .content(userMockHelper.getUserInfoRequest())
//            .contentType(APPLICATION_JSON)
//            .accept(APPLICATION_JSON)
//    );
//
//    /**
//     * then
//     * RestDoc 참고링크 : https://techblog.woowahan.com/2597/
//     * 참고링크 2 : https://jaehun2841.github.io/2019/08/04/2019-08-04-spring-rest-docs/#spring-rest-docs-architecture
//     * */
//    resultActions.andExpect(status().isOk())
//        .andDo(document("user/password/change",
//            preprocessRequest(prettyPrint()),
//            preprocessResponse(prettyPrint()),
//            requestFields(
//                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
//                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
//                fieldWithPath("secretCode").type(JsonFieldType.STRING).description("인증코드")
//            ),
//            responseFields(
//                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
//                fieldWithPath("message").type(JsonFieldType.NULL).description("메시지"),
//                fieldWithPath("data.result").type(JsonFieldType.STRING).description("데이터")
//            )
//            )
//        );






  }


}
