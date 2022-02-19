package com.realestate.service.property.address.entity;

import static com.realestate.service.property.constant.ContractType.JEONSE;
import static com.realestate.service.property.constant.ResidentialType.APARTMENT;
import static com.realestate.service.property.constant.StructureType.THREE_ROOM;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import com.realestate.service.config.TestConfig;
import com.realestate.service.property.entity.Property;
import com.realestate.service.property.entity.PropertyFloor;
import com.realestate.service.property.entity.PropertyInformation;
import com.realestate.service.property.entity.PropertyPrice;
import com.realestate.service.property.entity.PropertyRepository;
import com.realestate.service.user.constant.Role;
import com.realestate.service.user.constant.Status;
import com.realestate.service.user.entity.User;
import com.realestate.service.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DisplayName("PropertyAddressRepository 테스트")
@ActiveProfiles("test")
@Import(TestConfig.class)
@DataJpaTest
class PropertyAddressRepositoryTest {

  @Autowired
  UserRepository userRepository;

  @Autowired
  PropertyRepository propertyRepository;
  @Autowired
  PropertyAddressRepository propertyAddressRepository;

  User dummyUser = null;
  final String givenEmail = "test123@naver.com";
  final String givenPassword = "12341234";
  final String givenNickName = "givenNickName";

  @BeforeEach
  void setUp() {
    User givenUser = User.createUser(
        givenEmail,
        givenPassword,
        givenNickName,
        Status.ACTIVE,
        Role.NORMAL);

    dummyUser = userRepository.save(givenUser);
  }

  @AfterEach
  void after() {
    propertyRepository.deleteAll();
    propertyAddressRepository.deleteAll();
    userRepository.deleteAll();
  }

  @Nested
  @DisplayName("findByProperty 메서드는")
  class DescribeOf_findByIdAndUserId {
    final long givenSellPrice = 900000000L;
    final long givenDeposit = 15000000L;
    final int givenAdminPrice = 250000;
    final int givenMonthlyPrice = 400000;
    final int givenArea = 38500;
    final int givenFloor = 8;
    final int givenTopFloor = 15;
    final String givenTitle = "findTestTitle";
    final String givenContent = "findTestContent";

    final String givenAddress = "서울특별시 은평구 xxx동 xx호";
    final String givenRoadAddress = "서울특별시 은평구 xxx로 11가길";
    final String givenCity = "서울";
    final String givenZipcode = "03348";
    final double givenLatitude = 259.9155493215001d;
    final double givenLongitude = 99.8888888888888d;

    private Property subject() {
      var givenPropertyPrice = PropertyPrice.builder()
          .sellPrice(givenSellPrice)
          .deposit(givenDeposit)
          .adminPrice(givenAdminPrice)
          .monthlyPrice(givenMonthlyPrice)
          .build();

      var givenPropertyInformation = PropertyInformation.builder()
          .area(givenArea)
          .completionDate(LocalDate.now())
          .moveInDate(LocalDate.now())
          .propertyPrice(givenPropertyPrice)
          .propertyFloor(new PropertyFloor(givenFloor, givenTopFloor))
          .availableParking(true)
          .residentialType(APARTMENT)
          .structureType(THREE_ROOM)
          .contractType(JEONSE)
          .build();

      return Property.builder()
          .user(dummyUser)
          .title(givenTitle)
          .content(givenContent)
          .propertyInformation(givenPropertyInformation)
          .build();
    }

    private PropertyAddress subjectAddress(Property givenProperty) {
      return PropertyAddress.builder()
          .address(givenAddress)
          .roadAddress(givenRoadAddress)
          .city(givenCity)
          .zipcode(givenZipcode)
          .property(givenProperty)
          .latitude(givenLatitude)
          .longitude(givenLongitude)
          .build();
    }

    @Nested
    @DisplayName("매물 정보가 주어질 경우")
    class ContextWith_propertyIdAndUserIdCondition {

      @Test
      @DisplayName("조건에 해당하는 매물 주소 정보를 반환한다.")
      void it_returns() {

        // given
        Property savedProperty = propertyRepository.save(subject());
        PropertyAddress savedPropertyAddress = propertyAddressRepository.save(subjectAddress(savedProperty));

        // when
        PropertyAddress findPropertyAddress = propertyAddressRepository.findByProperty(savedProperty).get();

        // then
        assertThat(findPropertyAddress).isNotNull();
        assertThat(findPropertyAddress.getId()).isEqualTo(savedPropertyAddress.getId());
        assertThat(findPropertyAddress.getAddress()).isEqualTo(givenAddress);
        assertThat(findPropertyAddress.getLatitude()).isEqualTo(givenLatitude);

      }
    }
  }

}
