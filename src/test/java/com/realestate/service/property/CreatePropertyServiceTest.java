package com.realestate.service.property;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.util.Optional;

import com.realestate.service.property.constant.ResidentialType;
import com.realestate.service.property.constant.StructureType;
import com.realestate.service.property.dto.CreatePropertyCommand;
import com.realestate.service.property.entity.Property;
import com.realestate.service.property.entity.PropertyRepository;
import com.realestate.service.user.entity.User;
import com.realestate.service.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("CreatePropertyService 테스트")
@ExtendWith(MockitoExtension.class)
class CreatePropertyServiceTest {

  PropertyServiceHelper helper = new PropertyServiceHelper();

  @InjectMocks
  CreatePropertyService createPropertyService;

  @Mock
  UserRepository userRepository;

  @Mock
  PropertyRepository propertyRepository;

  @BeforeEach
  void setUp() {
    User savedUser = userRepository.save(helper.savedUser());

    given(userRepository.findById(anyLong()))
        .willReturn(Optional.of(helper.savedUser()));

    given(propertyRepository.save(any()))
        .willReturn(helper.createdProperty(savedUser));
  }

  @Nested
  @DisplayName("create 메서드는")
  class DescribeOf_create {
    final long givenUserId = 1L;
    final long givenSellPrice = 400000000L;
    final long givenDeposit = 20000000L;
    final int givenAdminPrice = 300000;
    final int givenMonthlyPrice = 200000;
    final int givenArea = 200000;
    final int givenFloor = 1;
    final int givenTopFloor = 4;
    final String givenTitle = "testTitle";
    final String givenContent = "test";

    private CreatePropertyCommand subject() {
      return CreatePropertyCommand.builder()
          .userId(givenUserId)
          .title(givenTitle)
          .content(givenContent)
          .area(givenArea)
          .availableParking(true)
          .deposit(givenDeposit)
          .estDate(LocalDate.now())
          .moveInDate(LocalDate.now())
          .adminPrice(givenAdminPrice)
          .sellPrice(givenSellPrice)
          .monthlyPrice(givenMonthlyPrice)
          .residentialType(ResidentialType.APARTMENT)
          .structureType(StructureType.TREE_ROOM)
          .floor(givenFloor)
          .topFloor(givenTopFloor)
          .build();
    }

    @Nested
    @DisplayName("매물 정보가 주어질 경우")
    class ContextWith_createdPropertyByCreateCommand {
      @Test
      @DisplayName("매물을 생성한 후 생성된 매물 정보를 반환한다.")
      void it_returns() {
        // given
        var command = subject();

        // when
        Property createdProperty = createPropertyService.create(command);

        // then
        assertThat(createdProperty.getTitle()).isEqualTo(givenTitle);
        assertThat(createdProperty.getPropertyInformation().getAvailableParking()).isTrue();
        assertThat(createdProperty.getPropertyInformation().getPropertyPrice().getSellPrice()).isEqualTo(givenSellPrice);
        assertThat(createdProperty.getPropertyInformation().getPropertyFloor().getTopFloor()).isEqualTo(givenTopFloor);

      }
    }
  }

}
