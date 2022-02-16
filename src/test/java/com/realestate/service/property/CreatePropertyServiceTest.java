package com.realestate.service.property;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.realestate.service.property.address.entity.PropertyAddressRepository;
import com.realestate.service.property.constant.ContractType;
import com.realestate.service.property.constant.ResidentialType;
import com.realestate.service.property.constant.StructureType;
import com.realestate.service.property.dto.CreatePropertyCommand;
import com.realestate.service.property.entity.Property;
import com.realestate.service.property.entity.PropertyRepository;
import com.realestate.service.user.repository.UserRepository;
import java.time.LocalDate;
import java.util.Optional;
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

  @Mock
  PropertyAddressRepository propertyAddressRepository;

  @BeforeEach
  void setUp() {
    var savedUser = userRepository.save(helper.savedUser());
    var savedProperty = helper.createdProperty(savedUser);

    given(userRepository.findById(anyLong()))
        .willReturn(Optional.of(helper.savedUser()));

    given(propertyRepository.save(any()))
        .willReturn(savedProperty);

    given(propertyAddressRepository.save(any()))
        .willReturn(helper.createdPropertyAddress(savedProperty));
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
    final String givenCity = "givenCity";
    final String givenAddress = "givenAddress";
    final String givenRoadAddress = "givenRoadAddress";
    final double givenLatitude = 126.917885535023d;
    final double givenLongitude = 37.5280674292228d;


    private CreatePropertyCommand subject() {
      var informationCommand = CreatePropertyCommand.PropertyInformationCommand.builder()
          .title(givenTitle)
          .content(givenContent)
          .area(givenArea)
          .availableParking(true)
          .deposit(givenDeposit)
          .completionDate(LocalDate.now())
          .moveInDate(LocalDate.now())
          .adminPrice(givenAdminPrice)
          .sellPrice(givenSellPrice)
          .monthlyPrice(givenMonthlyPrice)
          .residentialType(ResidentialType.APARTMENT)
          .structureType(StructureType.TREE_ROOM)
          .contractType(ContractType.SALE)
          .floor(givenFloor)
          .topFloor(givenTopFloor)
          .build();


      var addressCommand = CreatePropertyCommand.PropertyAddressCommand.builder()
          .city(givenCity)
          .address(givenAddress)
          .roadAddress(givenRoadAddress)
          .latitude(givenLatitude)
          .longitude(givenLongitude)
          .build();


      return new CreatePropertyCommand(givenUserId, informationCommand, addressCommand);
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
