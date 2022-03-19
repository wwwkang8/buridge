package com.realestate.service.property;

import com.realestate.service.property.entity.PropertyDataResponse;
import com.realestate.service.property.entity.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FindPropertyService implements FindPropertyUseCase {

  private final PropertyRepository propertyRepository;

  @Transactional(readOnly = true)
  @Override
  public Slice<PropertyDataResponse> find(FindPropertyCommand command) {
    return propertyRepository.find(command.toQueryDto());
  }
}
