package com.realestate.service.web.property;

import static com.realestate.service.utils.RestDocFormatGenerator.getDateTimeFormat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import com.realestate.service.config.WebSecurityConfig;
import com.realestate.service.property.CreatePropertyUseCase;
import com.realestate.service.user.jwt.JwtAuthenticationEntryPoint;
import com.realestate.service.user.jwt.JwtRequestFilter;
import com.realestate.service.user.jwt.JwtTokenUtil;
import com.realestate.service.user.jwt.JwtUserDetailService;
import com.realestate.service.user.service.PasswordEncoderService;
import com.realestate.service.web.property.response.CreatePropertyResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@DisplayName("매물 정보")
@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(CreatePropertyRestController.class)
@MockBean(JpaMetamodelMappingContext.class)
@ActiveProfiles("test")
public class CreatePropertyRestDoc {

  final PropertyMockHelper helper = new PropertyMockHelper();

  MockMvc mockMvc;

  @MockBean
  CreatePropertyUseCase createPropertyUseCase;

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
  void setUp(WebApplicationContext webApplicationContext,
             RestDocumentationContextProvider restDocumentation) {
    this.mockMvc = MockMvcBuilders
        .webAppContextSetup(webApplicationContext)
        .apply(documentationConfiguration(restDocumentation))
        .alwaysDo(print())
        .addFilters(new CharacterEncodingFilter("UTF-8", true))
        .build();

    Mockito.mockStatic(CreatePropertyResponse.class);
  }


  @Test
  @DisplayName("매물 등록 API")
  void create() throws Exception {
    var property = helper.createdProperty();
    var response = helper.getCreatePropertyResponse();

    given(createPropertyUseCase.create(any()))
        .willReturn(property);

    given(CreatePropertyResponse.of(any()))
        .willReturn(response);

    // when
    final ResultActions resultActions = mockMvc.perform(
        post("/api/properties")
            .content(helper.getCreatePropertyRequest())
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
    );

    resultActions
        .andExpect(status().isOk())
        .andDo(
            document("properties/{method-name}",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    requestSnippets()
                ),
                responseFields(
                    beneathPath("data").withSubsectionId("data"),
                    resultsSnippets()
                )
        ));
  }

  private List<FieldDescriptor> requestSnippets() {
    return List.of(
        fieldWithPath("title").type(STRING).description("제목"),
        fieldWithPath("content").type(STRING).description("내용"),
        fieldWithPath("information.area").type(NUMBER).description("면적"),
        fieldWithPath("information.structureType").type(STRING).description("구조 타입 <<StructureType>>"),
        fieldWithPath("information.contractType").type(STRING).description("거래 타입 <<ContractType>>"),
        fieldWithPath("information.sellPrice").type(NUMBER).description("매매 금액").optional(),
        fieldWithPath("information.deposit").type(NUMBER).description("보증금").optional(),
        fieldWithPath("information.monthlyPrice").type(NUMBER).description("월세").optional(),
        fieldWithPath("information.adminPrice").type(NUMBER).description("관리비").optional(),
        fieldWithPath("information.residentialType").type(STRING).description("거주 타입 <<ResidentialType>>"),
        fieldWithPath("information.floor").type(NUMBER).description("층"),
        fieldWithPath("information.topFloor").type(NUMBER).description("최고층"),
        fieldWithPath("information.availableParking").type(BOOLEAN).description("주차 가능 여부"),
        fieldWithPath("information.moveInDate").type(STRING).description("입주 가능일자"),
        fieldWithPath("information.completionDate").type(STRING).description("준공일자"),
        fieldWithPath("address.city").type(STRING).description("도시"),
        fieldWithPath("address.address").type(STRING).description("주소"),
        fieldWithPath("address.roadAddress").type(STRING).description("도로명 주소"),
        fieldWithPath("address.zipcode").type(STRING).description("우편번호"),
        fieldWithPath("address.latitude").type(NUMBER).description("위도"),
        fieldWithPath("address.longitude").type(NUMBER).description("경도"));
  }

  private List<FieldDescriptor> resultsSnippets() {
    return List.of(
        fieldWithPath("id").type(NUMBER).description("매물 번호"),
        fieldWithPath("title").type(STRING).description("제목"),
        fieldWithPath("content").type(STRING).description("내용"),
        fieldWithPath("createdDateTime").type(STRING).description("등록 일시").attributes(getDateTimeFormat())
    );
  }


}
