package com.realestate.service.property;

import com.realestate.service.property.dto.CreatePropertyCommand;
import com.realestate.service.property.entity.Property;
import com.realestate.service.property.entity.PropertyRepository;
import com.realestate.service.user.entity.User;
import com.realestate.service.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CreatePropertyService implements CreatePropertyUseCase {

  private final PropertyRepository propertyRepository;
  private final UserRepository userRepository;

  @Transactional
  @Override
  public Property create(CreatePropertyCommand command) {
    User findUser = findUserById(command.getUserId());
    return propertyRepository.save(command.toEntity(findUser));
  }

  private User findUserById(long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException(
            String.format("회원 정보가 존재하지 않습니다.[회원 번호 : %d]", userId)
        ));
  }
}
