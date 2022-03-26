package com.realestate.service.property;

import com.realestate.service.property.entity.PropertyDataResponse;
import com.realestate.service.property.entity.PropertyDetailDataResponse;
import org.springframework.data.domain.Slice;

public interface FindPropertyUseCase {

  /**
   * 매물 고유 번호에 맞는 매물 상세 정보를 반환합니다.
   *
   * @param id 매물 고유 번호
   * @return   매물 상세 정보
   */
  PropertyDetailDataResponse find(long id);

  /**
   * 조회 조건에 맞는 매물 정보 목록을 반환합니다.
   */
  Slice<PropertyDataResponse> find(FindPropertyCommand command);
}
