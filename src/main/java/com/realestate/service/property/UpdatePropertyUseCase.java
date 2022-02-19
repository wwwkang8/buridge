package com.realestate.service.property;

import com.realestate.service.property.dto.UpdatePropertyCommand;
import com.realestate.service.property.entity.Property;

/**
 * 매물 수정을 담당합니다.
 */
public interface UpdatePropertyUseCase {
  /**
   * 매물을 수정하고 수정된 매물을 리턴합니다.
   */
  Property update(long propertyId, UpdatePropertyCommand command);
  
}
