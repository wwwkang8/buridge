package com.realestate.service.property;

import com.realestate.service.property.address.entity.PropertyAddress;
import com.realestate.service.property.address.entity.PropertyAddressRepository;
import com.realestate.service.property.dto.UpdatePropertyCommand;
import com.realestate.service.property.entity.Property;
import com.realestate.service.property.entity.PropertyFloor;
import com.realestate.service.property.entity.PropertyInformation;
import com.realestate.service.property.entity.PropertyPrice;
import com.realestate.service.property.entity.PropertyRepository;
import com.realestate.service.property.exception.PropertyAddressNotFoundException;
import com.realestate.service.property.exception.PropertyNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UpdatePropertyService implements UpdatePropertyUseCase {

  private final PropertyRepository propertyRepository;
  private final PropertyAddressRepository propertyAddressRepository;

  @Transactional
  @Override
  public Property update(long propertyId, UpdatePropertyCommand command) {
    Property findProperty = propertyRepository.findByIdAndUserId(propertyId, command.getUserId())
        .orElseThrow(() -> new PropertyNotFoundException(propertyId));

    PropertyAddress findPropertyAddress = propertyAddressRepository.findByProperty(findProperty)
        .orElseThrow(() -> new PropertyAddressNotFoundException(propertyId));

    updateProperty(command, findProperty);

    updatePropertyAddress(command, findPropertyAddress);

    return findProperty;
  }

  private void updateProperty(UpdatePropertyCommand command, Property findProperty) {
    findProperty.update(
        command.getPropertyInformationCommand().getTitle(),
        command.getPropertyInformationCommand().getContent(),
        command.createPropertyInformation()
    );
  }

  private void updatePropertyAddress(UpdatePropertyCommand command,
                                     PropertyAddress findPropertyAddress) {
    var addressCommand = command.getPropertyAddressCommand();
    findPropertyAddress.update(
        addressCommand.getCity(),
        addressCommand.getAddress(),
        addressCommand.getRoadAddress(),
        addressCommand.getZipcode(),
        addressCommand.getLatitude(),
        addressCommand.getLongitude()
    );
  }
}
