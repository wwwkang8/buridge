package com.realestate.service.web.property;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.OBJECT;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.realestate.service.config.WebSecurityConfig;
import com.realestate.service.property.FindPropertyUseCase;
import com.realestate.service.user.jwt.JwtRequestFilter;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@DisplayName("매물 조회 정보")
@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(controllers = FindPropertyRestController.class, excludeFilters = {
    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
        WebSecurityConfig.class,
        JwtRequestFilter.class
    })
})
@MockBean(JpaMetamodelMappingContext.class)
@ActiveProfiles("test")
public class FindPropertyRestDoc {

  final PropertyMockHelper helper = new PropertyMockHelper();

  MockMvc mockMvc;

  @MockBean
  FindPropertyUseCase findPropertyUseCase;

  @BeforeEach
  void setUp(WebApplicationContext webApplicationContext,
             RestDocumentationContextProvider restDocumentation) {
    this.mockMvc = MockMvcBuilders
        .webAppContextSetup(webApplicationContext)
        .apply(documentationConfiguration(restDocumentation))
        .alwaysDo(print())
        .addFilters(new CharacterEncodingFilter("UTF-8", true))
        .build();

  }

  @Test
  @DisplayName("매물 상세 조회 API")
  void findOne() throws Exception {
    given(findPropertyUseCase.find(anyLong()))
        .willReturn(helper.getFindPropertyResponse());

    final ResultActions resultActions = mockMvc.perform(
        get("/api/properties/{id}", 1L));

    resultActions
        .andExpect(status().isOk())
        .andDo(
            document("properties/{method-name}",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                    beneathPath("data").withSubsectionId("data"),
                    getFieldDescriptors()
                )
            ));
  }

  private List<FieldDescriptor> getFieldDescriptors() {
    return List.of(
        fieldWithPath("userNumber").type(NUMBER).description("작성자 고유번호"),
        fieldWithPath("userEmail").type(STRING).description("작성자 이메일"),
        fieldWithPath("nickName").type(STRING).description("작성자 닉네임"),
        fieldWithPath("id").type(NUMBER).description("매물 고유번호"),
        fieldWithPath("title").type(STRING).description("제목"),
        fieldWithPath("content").type(STRING).description("내용"),
        fieldWithPath("area").type(NUMBER).description("면적"),
        fieldWithPath("structureType").type(STRING).description("매물 구조 타입 <<StructureType>>"),
        fieldWithPath("contractType").type(STRING).description("계약 타입 <<ContractType>>"),
        fieldWithPath("residentialType").type(STRING).description("거주 타입 <<ResidentialType>>"),
        fieldWithPath("availableParking").type(BOOLEAN).description("주차 가능 여부"),
        fieldWithPath("moveInDate").type(STRING).description("입주 가능 일자"),
        fieldWithPath("completionDate").type(STRING).description("준공 일자"),
        fieldWithPath("sellPrice").type(NUMBER).description("매매 금액").optional(),
        fieldWithPath("deposit").type(NUMBER).description("보증금").optional(),
        fieldWithPath("monthlyPrice").type(NUMBER).description("월세").optional(),
        fieldWithPath("adminPrice").type(NUMBER).description("관리비").optional(),
        fieldWithPath("floor").type(NUMBER).description("층"),
        fieldWithPath("topFloor").type(NUMBER).description("최고 층"),
        fieldWithPath("city").type(STRING).description("도시"),
        fieldWithPath("address").type(STRING).description("주소"),
        fieldWithPath("roadAddress").type(STRING).description("도로명 주소"),
        fieldWithPath("zipcode").type(STRING).description("우편 번호"),
        fieldWithPath("latitude").type(NUMBER).description("위도"),
        fieldWithPath("longitude").type(NUMBER).description("경도"),
        fieldWithPath("createdDateTime").type(STRING).description("생성 일시"),
        fieldWithPath("modifiedDateTime").type(STRING).description("수정 일시")
    );
  }

  @Test
  @DisplayName("매물 목록 조회 API")
  void findList() throws Exception {

    given(findPropertyUseCase.find(any()))
        .willReturn(helper.getFindPropertyPageResponse());

    // when
    final ResultActions resultActions = mockMvc.perform(
        get("/api/properties")
            .param("city", "서울")
            .param("keywordType", "TITLE")
            .param("searchText", "서울")
            .param("address", "서울특별시 은평구")
            .param("structureType", "THREE_ROOM")
            .param("contractType", "SALE")
            .param("residentialType", "APARTMENT")
            .param("availableParking", "true")
            .param("page", "1")
            .param("pageSize", "10")
    );

    resultActions
        .andExpect(status().isOk())
        .andDo(
            document("properties/{method-name}",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestParameters(
                    getRequestParameterDescriptors()
                ),
                responseFields(
                    beneathPath("data").withSubsectionId("data"),
                    getResponseFieldDescriptors()
                )
            ));
  }

  private List<ParameterDescriptor> getRequestParameterDescriptors() {
    return List.of(parameterWithName("city")
        .description("도시").optional(),
        parameterWithName("keywordType")
            .description("검색 키워드 타입 <<KeywordType>>").optional(),
        parameterWithName("searchText")
            .description("검색어").optional(),
        parameterWithName("address")
            .description("주소").optional(),
        parameterWithName("structureType")
            .description("매물 구조 타입 <<StructureType>>").optional(),
        parameterWithName("contractType")
            .description("계약 타입 <<ContractType>>").optional(),
        parameterWithName("residentialType")
            .description("거주 타입 <<ResidentialType>>").optional(),
        parameterWithName("availableParking")
            .description("주차 가능 여부").optional(),
        parameterWithName("page")
            .description("페이지 번호"),
        parameterWithName("pageSize")
            .description("페이지 사이즈"));
  }

  private List<FieldDescriptor> getResponseFieldDescriptors() {
    return List.of(
        fieldWithPath("content[]").type(ARRAY).description("매물 목록"),
        fieldWithPath("content[].userNumber").type(NUMBER).description("작성자 고유번호"),
        fieldWithPath("content[].id").type(NUMBER).description("매물 고유번호"),
        fieldWithPath("content[].title").type(STRING).description("제목"),
        fieldWithPath("content[].content").type(STRING).description("내용"),
        fieldWithPath("content[].structureType").type(STRING).description("매물 구조 타입 <<StructureType>>"),
        fieldWithPath("content[].contractType").type(STRING).description("계약 타입 <<ContractType>>"),
        fieldWithPath("content[].residentialType").type(STRING).description("거주 타입 <<ResidentialType>>"),
        fieldWithPath("content[].availableParking").type(BOOLEAN).description("주차 가능 여부"),
        fieldWithPath("content[].sellPrice").type(NUMBER).description("매매 금액").optional(),
        fieldWithPath("content[].deposit").type(NUMBER).description("보증금").optional(),
        fieldWithPath("content[].monthlyPrice").type(NUMBER).description("월세").optional(),
        fieldWithPath("content[].adminPrice").type(NUMBER).description("관리비").optional(),
        fieldWithPath("content[].floor").type(NUMBER).description("층"),
        fieldWithPath("content[].topFloor").type(NUMBER).description("최고 층"),
        fieldWithPath("content[].city").type(STRING).description("도시"),
        fieldWithPath("content[].address").type(STRING).description("주소"),
        fieldWithPath("content[].roadAddress").type(STRING).description("도로명 주소"),
        fieldWithPath("content[].zipcode").type(STRING).description("우편 번호"),
        fieldWithPath("content[].latitude").type(NUMBER).description("위도"),
        fieldWithPath("content[].longitude").type(NUMBER).description("경도"),
        fieldWithPath("pageable").type(OBJECT).description("페이징 설정 정보"),
        fieldWithPath("pageable.pageNumber").type(NUMBER).description("페이징 번호"),
        fieldWithPath("pageable.pageSize").type(NUMBER).description("페이징 사이즈"),
        fieldWithPath("pageable.offset").type(NUMBER).description("페이징 offset"),
        fieldWithPath("pageable.paged").type(BOOLEAN).description("페이징 적용 여부"),
        fieldWithPath("pageable.unpaged").type(BOOLEAN).description("페이징 비적용 여부"),
        fieldWithPath("pageable.sort").type(OBJECT).description("정렬"),
        fieldWithPath("pageable.sort.sorted").type(BOOLEAN).description("정렬 여부"),
        fieldWithPath("pageable.sort.unsorted").type(BOOLEAN).description("비정렬 여부"),
        fieldWithPath("pageable.sort.empty").type(BOOLEAN).description("정렬 객체의 공백여부"),
        fieldWithPath("number").type(NUMBER).description("페이지 번호"),
        fieldWithPath("first").type(BOOLEAN).description("첫번째 페이지 여부"),
        fieldWithPath("last").type(BOOLEAN).description("마지막 페이지 여부"),
        fieldWithPath("numberOfElements").type(NUMBER).description("현재 페이지 내 엘리먼트 개수"),
        fieldWithPath("size").type(NUMBER).description("페이징 사이즈"),
        fieldWithPath("empty").type(BOOLEAN).description("페이지가 비어있는지 여부"),
        fieldWithPath("sort").type(OBJECT).description("정렬 정보"),
        fieldWithPath("sort.sorted").type(BOOLEAN).description("정렬 여부"),
        fieldWithPath("sort.unsorted").type(BOOLEAN).description("비정렬 여부"),
        fieldWithPath("sort.empty").type(BOOLEAN).description("정렬 객체의 공백 여부")
    );
  }
}
