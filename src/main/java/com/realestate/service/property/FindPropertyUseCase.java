package com.realestate.service.property;

import com.realestate.service.property.entity.PropertyDataResponse;
import org.springframework.data.domain.Slice;

public interface FindPropertyUseCase {
  /**
   * 조회 조건에 맞는 매물 정보 목록을 반환합니다.
   */
  Slice<PropertyDataResponse> find(FindPropertyCommand command);
}
