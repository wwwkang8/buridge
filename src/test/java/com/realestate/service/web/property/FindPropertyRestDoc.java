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

@DisplayName("?????? ?????? ??????")
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
  @DisplayName("?????? ?????? ?????? API")
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
        fieldWithPath("userNumber").type(NUMBER).description("????????? ????????????"),
        fieldWithPath("userEmail").type(STRING).description("????????? ?????????"),
        fieldWithPath("nickName").type(STRING).description("????????? ?????????"),
        fieldWithPath("id").type(NUMBER).description("?????? ????????????"),
        fieldWithPath("title").type(STRING).description("??????"),
        fieldWithPath("content").type(STRING).description("??????"),
        fieldWithPath("area").type(NUMBER).description("??????"),
        fieldWithPath("structureType").type(STRING).description("?????? ?????? ?????? <<StructureType>>"),
        fieldWithPath("contractType").type(STRING).description("?????? ?????? <<ContractType>>"),
        fieldWithPath("residentialType").type(STRING).description("?????? ?????? <<ResidentialType>>"),
        fieldWithPath("availableParking").type(BOOLEAN).description("?????? ?????? ??????"),
        fieldWithPath("moveInDate").type(STRING).description("?????? ?????? ??????"),
        fieldWithPath("completionDate").type(STRING).description("?????? ??????"),
        fieldWithPath("sellPrice").type(NUMBER).description("?????? ??????").optional(),
        fieldWithPath("deposit").type(NUMBER).description("?????????").optional(),
        fieldWithPath("monthlyPrice").type(NUMBER).description("??????").optional(),
        fieldWithPath("adminPrice").type(NUMBER).description("?????????").optional(),
        fieldWithPath("floor").type(NUMBER).description("???"),
        fieldWithPath("topFloor").type(NUMBER).description("?????? ???"),
        fieldWithPath("city").type(STRING).description("??????"),
        fieldWithPath("address").type(STRING).description("??????"),
        fieldWithPath("roadAddress").type(STRING).description("????????? ??????"),
        fieldWithPath("zipcode").type(STRING).description("?????? ??????"),
        fieldWithPath("latitude").type(NUMBER).description("??????"),
        fieldWithPath("longitude").type(NUMBER).description("??????"),
        fieldWithPath("createdDateTime").type(STRING).description("?????? ??????"),
        fieldWithPath("modifiedDateTime").type(STRING).description("?????? ??????")
    );
  }

  @Test
  @DisplayName("?????? ?????? ?????? API")
  void findList() throws Exception {

    given(findPropertyUseCase.find(any()))
        .willReturn(helper.getFindPropertyPageResponse());

    // when
    final ResultActions resultActions = mockMvc.perform(
        get("/api/properties")
            .param("city", "??????")
            .param("keywordType", "TITLE")
            .param("searchText", "??????")
            .param("address", "??????????????? ?????????")
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
        .description("??????").optional(),
        parameterWithName("keywordType")
            .description("?????? ????????? ?????? <<KeywordType>>").optional(),
        parameterWithName("searchText")
            .description("?????????").optional(),
        parameterWithName("address")
            .description("??????").optional(),
        parameterWithName("structureType")
            .description("?????? ?????? ?????? <<StructureType>>").optional(),
        parameterWithName("contractType")
            .description("?????? ?????? <<ContractType>>").optional(),
        parameterWithName("residentialType")
            .description("?????? ?????? <<ResidentialType>>").optional(),
        parameterWithName("availableParking")
            .description("?????? ?????? ??????").optional(),
        parameterWithName("page")
            .description("????????? ??????"),
        parameterWithName("pageSize")
            .description("????????? ?????????"));
  }

  private List<FieldDescriptor> getResponseFieldDescriptors() {
    return List.of(
        fieldWithPath("content[]").type(ARRAY).description("?????? ??????"),
        fieldWithPath("content[].userNumber").type(NUMBER).description("????????? ????????????"),
        fieldWithPath("content[].id").type(NUMBER).description("?????? ????????????"),
        fieldWithPath("content[].title").type(STRING).description("??????"),
        fieldWithPath("content[].content").type(STRING).description("??????"),
        fieldWithPath("content[].structureType").type(STRING).description("?????? ?????? ?????? <<StructureType>>"),
        fieldWithPath("content[].contractType").type(STRING).description("?????? ?????? <<ContractType>>"),
        fieldWithPath("content[].residentialType").type(STRING).description("?????? ?????? <<ResidentialType>>"),
        fieldWithPath("content[].availableParking").type(BOOLEAN).description("?????? ?????? ??????"),
        fieldWithPath("content[].sellPrice").type(NUMBER).description("?????? ??????").optional(),
        fieldWithPath("content[].deposit").type(NUMBER).description("?????????").optional(),
        fieldWithPath("content[].monthlyPrice").type(NUMBER).description("??????").optional(),
        fieldWithPath("content[].adminPrice").type(NUMBER).description("?????????").optional(),
        fieldWithPath("content[].floor").type(NUMBER).description("???"),
        fieldWithPath("content[].topFloor").type(NUMBER).description("?????? ???"),
        fieldWithPath("content[].city").type(STRING).description("??????"),
        fieldWithPath("content[].address").type(STRING).description("??????"),
        fieldWithPath("content[].roadAddress").type(STRING).description("????????? ??????"),
        fieldWithPath("content[].zipcode").type(STRING).description("?????? ??????"),
        fieldWithPath("content[].latitude").type(NUMBER).description("??????"),
        fieldWithPath("content[].longitude").type(NUMBER).description("??????"),
        fieldWithPath("pageable").type(OBJECT).description("????????? ?????? ??????"),
        fieldWithPath("pageable.pageNumber").type(NUMBER).description("????????? ??????"),
        fieldWithPath("pageable.pageSize").type(NUMBER).description("????????? ?????????"),
        fieldWithPath("pageable.offset").type(NUMBER).description("????????? offset"),
        fieldWithPath("pageable.paged").type(BOOLEAN).description("????????? ?????? ??????"),
        fieldWithPath("pageable.unpaged").type(BOOLEAN).description("????????? ????????? ??????"),
        fieldWithPath("pageable.sort").type(OBJECT).description("??????"),
        fieldWithPath("pageable.sort.sorted").type(BOOLEAN).description("?????? ??????"),
        fieldWithPath("pageable.sort.unsorted").type(BOOLEAN).description("????????? ??????"),
        fieldWithPath("pageable.sort.empty").type(BOOLEAN).description("?????? ????????? ????????????"),
        fieldWithPath("number").type(NUMBER).description("????????? ??????"),
        fieldWithPath("first").type(BOOLEAN).description("????????? ????????? ??????"),
        fieldWithPath("last").type(BOOLEAN).description("????????? ????????? ??????"),
        fieldWithPath("numberOfElements").type(NUMBER).description("?????? ????????? ??? ???????????? ??????"),
        fieldWithPath("size").type(NUMBER).description("????????? ?????????"),
        fieldWithPath("empty").type(BOOLEAN).description("???????????? ??????????????? ??????"),
        fieldWithPath("sort").type(OBJECT).description("?????? ??????"),
        fieldWithPath("sort.sorted").type(BOOLEAN).description("?????? ??????"),
        fieldWithPath("sort.unsorted").type(BOOLEAN).description("????????? ??????"),
        fieldWithPath("sort.empty").type(BOOLEAN).description("?????? ????????? ?????? ??????")
    );
  }
}
