package com.realestate.service.property;

import com.realestate.service.property.entity.Property;

/**
 * 매물의 삭제을 담당합니다.
 */
public interface DeletePropertyUseCase {
  /**
   * 매물을 삭제 처리하고 삭제된 매물 정보를 반환합니다.
   */
  Property delete(long propertyId, long userId);
  
}
