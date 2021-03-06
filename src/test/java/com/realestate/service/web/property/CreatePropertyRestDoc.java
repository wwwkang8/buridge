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
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.realestate.service.config.WebSecurityConfig;
import com.realestate.service.property.CreatePropertyUseCase;
import com.realestate.service.user.jwt.JwtRequestFilter;
import com.realestate.service.web.property.response.CreatePropertyResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@DisplayName("?????? ?????? ??????")
@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(controllers = CreatePropertyRestController.class, excludeFilters = {
    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
        WebSecurityConfig.class,
        JwtRequestFilter.class
    })
})
@MockBean(JpaMetamodelMappingContext.class)
@ActiveProfiles("test")
public class CreatePropertyRestDoc {

  final PropertyMockHelper helper = new PropertyMockHelper();

  MockMvc mockMvc;

  @MockBean
  CreatePropertyUseCase createPropertyUseCase;

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
  @DisplayName("?????? ?????? API")
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
        fieldWithPath("title").type(STRING).description("??????"),
        fieldWithPath("content").type(STRING).description("??????"),
        fieldWithPath("information.area").type(NUMBER).description("??????"),
        fieldWithPath("information.structureType").type(STRING).description("?????? ?????? <<StructureType>>"),
        fieldWithPath("information.contractType").type(STRING).description("?????? ?????? <<ContractType>>"),
        fieldWithPath("information.sellPrice").type(NUMBER).description("?????? ??????").optional(),
        fieldWithPath("information.deposit").type(NUMBER).description("?????????").optional(),
        fieldWithPath("information.monthlyPrice").type(NUMBER).description("??????").optional(),
        fieldWithPath("information.adminPrice").type(NUMBER).description("?????????").optional(),
        fieldWithPath("information.residentialType").type(STRING).description("?????? ?????? <<ResidentialType>>"),
        fieldWithPath("information.floor").type(NUMBER).description("???"),
        fieldWithPath("information.topFloor").type(NUMBER).description("?????????"),
        fieldWithPath("information.availableParking").type(BOOLEAN).description("?????? ?????? ??????"),
        fieldWithPath("information.moveInDate").type(STRING).description("?????? ????????????"),
        fieldWithPath("information.completionDate").type(STRING).description("????????????"),
        fieldWithPath("address.city").type(STRING).description("??????"),
        fieldWithPath("address.address").type(STRING).description("??????"),
        fieldWithPath("address.roadAddress").type(STRING).description("????????? ??????"),
        fieldWithPath("address.zipcode").type(STRING).description("????????????"),
        fieldWithPath("address.latitude").type(NUMBER).description("??????"),
        fieldWithPath("address.longitude").type(NUMBER).description("??????"));
  }

  private List<FieldDescriptor> resultsSnippets() {
    return List.of(
        fieldWithPath("id").type(NUMBER).description("?????? ??????"),
        fieldWithPath("title").type(STRING).description("??????"),
        fieldWithPath("content").type(STRING).description("??????"),
        fieldWithPath("createdDateTime").type(STRING).description("?????? ??????").attributes(getDateTimeFormat())
    );
  }


}
