package com.realestate.service.web.user;

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

import com.realestate.service.user.jwt.JwtAuthenticationEntryPoint;
import com.realestate.service.user.jwt.JwtTokenUtil;
import com.realestate.service.user.jwt.JwtUserDetailService;
import com.realestate.service.user.service.UserService;
import com.realestate.service.web.user.response.SignupUserResponse;
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
  JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

  @MockBean
  JwtTokenUtil jwtTokenUtil;

  @MockBean
  JwtUserDetailService jwtUserDetailService;

  @MockBean
  AuthenticationManager authenticationManager;

  @MockBean
  UserService userService;
  

  @BeforeEach
  void setup(WebApplicationContext webApplicationContext
            , RestDocumentationContextProvider restDocumentationContextProvider) {

    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext) //Mock을 사용하기 위한 설정
                                  .apply(documentationConfiguration(restDocumentationContextProvider))// 문서화 하는 설정
                                  .alwaysDo(print())
                                  .addFilters(new CharacterEncodingFilter("UTF-8", true))
                                  .build();

    /***
     * 용도 : static 함수를 모킹하기 위해서는 Mockito.mockStatic 함수가 필요함.
     * 참고링크 : https://asolntsev.github.io/en/2020/07/11/mockito-static-methods/
     */
    Mockito.mockStatic(SignupUserResponse.class);

  }


  @Test
  @DisplayName("사용자 등록")
  void signup() throws Exception {

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
     * */
    resultActions.andExpect(status().isOk())
                 .andDo(document("user/create",
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


}
