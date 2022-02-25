package com.realestate.service.web.property;

import com.realestate.service.property.DeletePropertyUseCase;
import com.realestate.service.web.property.response.DeletePropertyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class DeletePropertyRestController {

  // TODO : Jwt 토큰 개발전 까지 사용될 userId 입니다.
  private static final long MOCK_USER_ID = 1L;

  private final DeletePropertyUseCase deletePropertyUseCase;

  @DeleteMapping("/api/properties/{propertyId}")
  public DeletePropertyResponse delete(@PathVariable Long propertyId) {
    return DeletePropertyResponse.of(deletePropertyUseCase.delete(propertyId, MOCK_USER_ID));
  }
}
