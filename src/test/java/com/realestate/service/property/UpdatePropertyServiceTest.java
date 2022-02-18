package com.realestate.service.property;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.util.Optional;

import com.realestate.service.property.address.entity.PropertyAddressRepository;
import com.realestate.service.property.constant.ContractType;
import com.realestate.service.property.constant.ResidentialType;
import com.realestate.service.property.constant.StructureType;
import com.realestate.service.property.dto.UpdatePropertyCommand;
import com.realestate.service.property.entity.Property;
import com.realestate.service.property.entity.PropertyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("UpdatePropertyService 테스트")
@ExtendWith(MockitoExtension.class)
class UpdatePropertyServiceTest {

  PropertyServiceHelper helper = new PropertyServiceHelper();

  @InjectMocks
  UpdatePropertyService updatePropertyService;

  @Mock
  PropertyRepository propertyRepository;

  @Mock
  PropertyAddressRepository propertyAddressRepository;


  @BeforeEach
  void setUp() {
    var savedUser = helper.savedUser();
    var savedProperty = helper.createdProperty(savedUser);
    given(propertyRepository.findByIdAndUserId(anyLong(), anyLong()))
        .willReturn(Optional.of(helper.createdProperty(savedUser)));

    given(propertyAddressRepository.findByProperty(any()))
        .willReturn(Optional.of(helper.createdPropertyAddress(savedProperty)));
  }

  @Nested
  @DisplayName("update 메서드는")
  class DescribeOf_update {
    final long givenPropertyId = 1L;
    final long givenUserId = 1L;
    final long givenSellPrice = 2549000000L;
    final long givenDeposit = 20000000L;
    final int givenAdminPrice = 300000;
    final int givenMonthlyPrice = 200000;
    final int givenArea = 200000;
    final int givenFloor = 1;
    final int givenTopFloor = 5;
    final String givenTitle = "updateTestTitle";
    final String givenContent = "updateTest";
    final String givenCity = "givenCity";
    final String givenAddress = "givenAddress";
    final String givenRoadAddress = "givenUpdateRoadAddress";
    final double givenLatitude = 126.917885535023d;
    final double givenLongitude = 37.5280674292228d;


    private UpdatePropertyCommand subject() {
      var informationCommand = UpdatePropertyCommand.PropertyInformationCommand.builder()
          .title(givenTitle)
          .content(givenContent)
          .area(givenArea)
          .availableParking(false)
          .deposit(givenDeposit)
          .completionDate(LocalDate.now())
          .moveInDate(LocalDate.now())
          .adminPrice(givenAdminPrice)
          .sellPrice(givenSellPrice)
          .monthlyPrice(givenMonthlyPrice)
          .residentialType(ResidentialType.APARTMENT)
          .structureType(StructureType.THREE_ROOM)
          .contractType(ContractType.SALE)
          .floor(givenFloor)
          .topFloor(givenTopFloor)
          .build();


      var addressCommand = UpdatePropertyCommand.PropertyAddressCommand.builder()
          .city(givenCity)
          .address(givenAddress)
          .roadAddress(givenRoadAddress)
          .latitude(givenLatitude)
          .longitude(givenLongitude)
          .build();


      return new UpdatePropertyCommand(givenUserId, informationCommand, addressCommand);
    }

    @Nested
    @DisplayName("매물 식별자 번호와 수정 정보가 주어질 경우")
    class ContextWith_updatedPropertyByUpdateCommand {
      @Test
      @DisplayName("매물을 찾은 후 매물 정보를 수정한다.")
      void it_returns() {
        // given
        var command = subject();

        // when
        Property updatedProperty = updatePropertyService.update(givenPropertyId, command);

        // then
        assertThat(updatedProperty.getTitle()).isEqualTo(givenTitle);
        assertThat(updatedProperty.getContent()).isEqualTo(givenContent);
        assertThat(updatedProperty.getPropertyInformation().getAvailableParking()).isFalse();
        assertThat(updatedProperty.getPropertyInformation().getPropertyPrice().getSellPrice()).isEqualTo(givenSellPrice);
        assertThat(updatedProperty.getPropertyInformation().getPropertyFloor().getTopFloor()).isEqualTo(givenTopFloor);

      }
    }
  }

}
