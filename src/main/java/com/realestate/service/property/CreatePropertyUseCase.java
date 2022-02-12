package com.realestate.service.property;

import com.realestate.service.property.dto.CreatePropertyCommand;
import com.realestate.service.property.entity.Property;

public interface CreatePropertyUseCase {
  /**
   * 매물을 등록하고 등록된 매물을 리턴합니다.
   */
  Property create(CreatePropertyCommand command);
}
