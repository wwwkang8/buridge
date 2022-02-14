package com.realestate.service.web.property;

import javax.validation.Valid;

import com.realestate.service.property.CreatePropertyUseCase;
import com.realestate.service.web.property.request.CreatePropertyRequest;
import com.realestate.service.web.property.response.CreatePropertyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CreatePropertyRestController {

  // TODO : Jwt 토큰 개발전 까지 사용될 userId 입니다.
  private static final long MOCK_USER_ID = 1L;

  private final CreatePropertyUseCase createPropertyUseCase;

  /**
   * 매물 등록 요청을 핸들링 합니다.
   *
   * @param request 등록 요청 정보.
   * @return 등록된 매물 정보.
   */
  @PostMapping("/api/properties")
  public CreatePropertyResponse create(@RequestBody @Valid CreatePropertyRequest request) {
    return CreatePropertyResponse.of(createPropertyUseCase
        .create(request.toCreatePropertyCommand(MOCK_USER_ID)));
  }
}
