package com.realestate.service.property;

import com.realestate.service.property.entity.Property;
import com.realestate.service.property.entity.PropertyRepository;
import com.realestate.service.property.exception.PropertyNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DeletePropertyService implements DeletePropertyUseCase {

  private final PropertyRepository propertyRepository;

  @Transactional
  @Override
  public Property delete(long propertyId, long userId) {
    Property findProperty = findByIdAndUserId(propertyId, userId);
    findProperty.delete();
    return findProperty;
  }

  private Property findByIdAndUserId(long propertyId, long userId) {
    return propertyRepository.findByIdAndUserId(propertyId, userId)
        .orElseThrow(() -> new PropertyNotFoundException(propertyId));
  }
}
