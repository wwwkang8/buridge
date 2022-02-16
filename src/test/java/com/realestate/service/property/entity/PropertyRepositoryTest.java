package com.realestate.service.property.entity;

import static com.realestate.service.property.constant.ContractType.*;
import static com.realestate.service.property.constant.ResidentialType.APARTMENT;
import static com.realestate.service.property.constant.StructureType.TREE_ROOM;
import static org.assertj.core.api.Assertions.assertThat;

import com.realestate.service.config.TestConfig;
import com.realestate.service.property.address.entity.PropertyAddress;
import com.realestate.service.property.address.entity.PropertyAddressRepository;
import com.realestate.service.property.constant.ContractType;
import com.realestate.service.property.image.constant.MimeType;
import com.realestate.service.property.image.entity.PropertyImage;
import com.realestate.service.property.image.entity.PropertyImageRepository;
import com.realestate.service.user.constant.Role;
import com.realestate.service.user.constant.Status;
import com.realestate.service.user.entity.User;
import com.realestate.service.user.repository.UserRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DisplayName("PropertyRepository 테스트")
@ActiveProfiles("test")
@Import(TestConfig.class)
@DataJpaTest
class PropertyRepositoryTest {

  @Autowired
  UserRepository userRepository;

  @Autowired
  PropertyRepository propertyRepository;
  @Autowired
  PropertyAddressRepository propertyAddressRepository;
  @Autowired
  PropertyImageRepository propertyImageRepository;

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
    userRepository.deleteAll();
  }

  @Nested
  @DisplayName("save 메서드는")
  class DescribeOf_save {
    final long givenSellPrice = 400000000L;
    final long givenDeposit = 20000000L;
    final int givenAdminPrice = 300000;
    final int givenMonthlyPrice = 200000;
    final int givenArea = 200000;
    final int givenFloor = 1;
    final int givenTopFloor = 4;
    final String givenTitle = "testTitle";
    final String givenContent = "test";

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
          .structureType(TREE_ROOM)
          .contractType(LARGE_DEPOSIT)
          .build();

      return Property.builder()
          .user(dummyUser)
          .title(givenTitle)
          .content(givenContent)
          .propertyInformation(givenPropertyInformation)
          .build();
    }

    @Nested
    @DisplayName("매물 정보가 주어질 경우")
    class ContextWith_createdPropertyByPropertyInfo {

      @Test
      @DisplayName("매물을 저장한 후 저장된 정보를 반환한다.")
      void it_returns() {

        // given
        Property givenProperty = subject();

        // when
        Property savedProperty = propertyRepository.save(givenProperty);

        // then
        assertThat(savedProperty).isNotNull();
        assertThat(savedProperty.getContent()).isEqualTo(givenContent);
        assertThat(savedProperty.getCreatedDateTime()).isBefore(LocalDateTime.now());
        assertThat(savedProperty.getPropertyInformation().getArea()).isEqualTo(givenArea);
        assertThat(savedProperty.getPropertyInformation().getContractType()).isEqualTo(LARGE_DEPOSIT);
        assertThat(savedProperty.getPropertyInformation().getPropertyPrice().getSellPrice()).isEqualTo(givenSellPrice);
        assertThat(savedProperty.getPropertyInformation().getPropertyFloor().getTopFloor()).isEqualTo(givenTopFloor);
      }
    }

    @Nested
    @DisplayName("매물 정보와 주소 정보 이미지 정보가 주어질 경우")
    class ContextWith_createdPropertyByPropertyInfoAddressInfoImageInfo {
      
      final String givenAddress = "서울특별시 서대문구 xxx동 xx호";
      final String givenRoadAddress = "서울특별시 서대문구 xxx로 11가길";
      final String givenCity = "서울";
      final String givenZipcode = "01185";
      final double givenLatitude = 126.917885535023d;
      final double givenLongitude = 37.5280674292228d;
      final String givenOriginalFileName = "givenOriginalFileName";
      final String givenHash = "givenHash";
      final String givenHost = "givenHost";
      final String givenPath = "givenPath";
      final int givenSize = 100;

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

      private PropertyImage subjectImage(Property givenProperty) {
        return  PropertyImage.builder()
            .originalFileName(givenOriginalFileName)
            .hash(givenHash)
            .host(givenHost)
            .mimeType(MimeType.JPEG)
            .path(givenPath)
            .size(givenSize)
            .property(givenProperty)
            .build();
      }

      @Test
      @DisplayName("매물을 저장한 후 저장된 정보를 반환한다.")
      void it_returns() {

        // given
        Property givenProperty = subject();
        PropertyAddress givenPropertyAddress = subjectAddress(givenProperty);
        PropertyImage givenPropertyImage = subjectImage(givenProperty);

        // when
        Property savedProperty = propertyRepository.save(givenProperty);
        PropertyAddress savedPropertyAddress = propertyAddressRepository.save(givenPropertyAddress);
        PropertyImage savedPropertyImage = propertyImageRepository.save(givenPropertyImage);

        // then
        assertThat(savedProperty).isNotNull();
        assertThat(savedProperty.getTitle()).isEqualTo(givenTitle);
        assertThat(savedPropertyAddress.getRoadAddress()).isEqualTo(givenRoadAddress);
        assertThat(savedPropertyAddress.getLatitude()).isEqualTo(givenLatitude);
        assertThat(savedPropertyImage.getSize()).isEqualTo(givenSize);
      }
    }
  }

}
