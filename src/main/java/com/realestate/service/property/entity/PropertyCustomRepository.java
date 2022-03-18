package com.realestate.service.property.entity;

import com.realestate.service.property.dto.FindPropertyQueryDto;
import org.springframework.data.domain.Slice;

public interface PropertyCustomRepository {
  /**
   * 요청에 맞는 매물 정보를 반환합니다.
   *
   * @param queryDto 매물 조회 요청 dto.
   */
  Slice<PropertyDataResponse> find(FindPropertyQueryDto queryDto);
}
