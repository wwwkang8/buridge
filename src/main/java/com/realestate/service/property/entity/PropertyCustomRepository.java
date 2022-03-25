package com.realestate.service.property.entity;

import java.util.Optional;

import com.realestate.service.property.dto.FindPropertyQueryDto;
import org.springframework.data.domain.Slice;

public interface PropertyCustomRepository {
  /**
   * 매물 번호에 맞는 매물 정보를 반환합니다.
   *
   * @param id 매물 고유 번호
   * @return   매물 상세 정보
   */
  Optional<PropertyDetailDataResponse> find(long id);

  /**
   * 요청에 맞는 매물 정보를 반환합니다.
   *
   * @param queryDto 매물 조회 요청 dto
   */
  Slice<PropertyDataResponse> find(FindPropertyQueryDto queryDto);
}
