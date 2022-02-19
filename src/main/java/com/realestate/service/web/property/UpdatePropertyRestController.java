package com.realestate.service.web.property;

import javax.validation.Valid;

import com.realestate.service.property.UpdatePropertyUseCase;
import com.realestate.service.web.property.request.UpdatePropertyRequest;
import com.realestate.service.web.property.response.UpdatePropertyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UpdatePropertyRestController {

  // TODO : Jwt 토큰 개발전 까지 사용될 userId 입니다.
  private static final long MOCK_USER_ID = 1L;

  private final UpdatePropertyUseCase updatePropertyUseCase;

  /**
   * 매물 수정 요청을 핸들링 합니다.
   *
   * @param propertyId 매물 식별자
   * @param request 수정 요청 정보
   * @return 수정된 매물 정보
   */
  @PutMapping("/api/properties/{propertyId}")
  public UpdatePropertyResponse update(@PathVariable Long propertyId,
                                       @RequestBody @Valid UpdatePropertyRequest request) {
    return UpdatePropertyResponse.of(updatePropertyUseCase.update(
        propertyId, request.toUpdatePropertyCommand(MOCK_USER_ID)));
  }
}
