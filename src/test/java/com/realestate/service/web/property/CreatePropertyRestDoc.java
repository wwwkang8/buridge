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
import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.realestate.service.property.CreatePropertyUseCase;
import com.realestate.service.web.property.response.CreatePropertyResponse;
import java.util.List;
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
                responseFields(
                    beneathPath("data").withSubsectionId("data"),
                    resultsSnippets()
                )
        ));
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
