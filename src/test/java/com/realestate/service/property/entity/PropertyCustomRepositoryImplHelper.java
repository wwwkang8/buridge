package com.realestate.service.property.entity;

import static com.realestate.service.property.constant.ContractType.JEONSE;
import static com.realestate.service.property.constant.ContractType.MONTHLY_LEASE;
import static com.realestate.service.property.constant.ContractType.SALE;
import static com.realestate.service.property.constant.ResidentialType.APARTMENT;
import static com.realestate.service.property.constant.ResidentialType.HOUSING;
import static com.realestate.service.property.constant.ResidentialType.OFFICE;
import static com.realestate.service.property.constant.ResidentialType.OFFICE_HOTEL;
import static com.realestate.service.property.constant.ResidentialType.STORE;
import static com.realestate.service.property.constant.StructureType.DUPLEX;
import static com.realestate.service.property.constant.StructureType.MORE_THAN_FOUR_ROOM;
import static com.realestate.service.property.constant.StructureType.ONE_ROOM;
import static com.realestate.service.property.constant.StructureType.THREE_ROOM;
import static com.realestate.service.property.constant.StructureType.TWO_ROOM;

import com.realestate.service.property.address.entity.PropertyAddress;
import com.realestate.service.user.constant.Role;
import com.realestate.service.user.constant.Status;
import com.realestate.service.user.entity.User;
import java.time.LocalDate;
import java.util.List;

public class PropertyCustomRepositoryImplHelper {
  protected User initUser() {
    final String givenEmail = "test123@naver.com";
    final String givenPassword = "12341234";
    final String givenNickName = "givenNickName";

    return User.createUser(
        givenEmail,
        givenPassword,
        givenNickName,
        Status.ACTIVE,
        Role.NORMAL);
  }

  protected List<Property> initProperty(User dummyUser) {
    var givenPropertyPrice1 = PropertyPrice.builder()
        .deposit(15000000L)
        .adminPrice(250000)
        .build();

    var givenPropertyInformation1 = PropertyInformation.builder()
        .area(65700)
        .completionDate(LocalDate.now())
        .moveInDate(LocalDate.now())
        .propertyPrice(givenPropertyPrice1)
        .propertyFloor(new PropertyFloor(8, 15))
        .availableParking(true)
        .residentialType(APARTMENT)
        .structureType(THREE_ROOM)
        .contractType(JEONSE)
        .build();

    Property dummyProperty1 = Property.builder()
        .user(dummyUser)
        .title("서대문구 전세")
        .content("서대문구 전세 입니다!!!")
        .propertyInformation(givenPropertyInformation1)
        .build();


    var givenPropertyPrice2 = PropertyPrice.builder()
        .deposit(10000000L)
        .adminPrice(50000)
        .monthlyPrice(560000)
        .build();

    var givenPropertyInformation2 = PropertyInformation.builder()
        .area(38500)
        .completionDate(LocalDate.now())
        .moveInDate(LocalDate.now())
        .propertyPrice(givenPropertyPrice2)
        .propertyFloor(new PropertyFloor(3, 22))
        .availableParking(false)
        .residentialType(OFFICE_HOTEL)
        .structureType(ONE_ROOM)
        .contractType(MONTHLY_LEASE)
        .build();

    Property dummyProperty2 = Property.builder()
        .user(dummyUser)
        .title("역삼동 오피스텔 원룸")
        .content("역삼동 원룸 꿀매~~~")
        .propertyInformation(givenPropertyInformation2)
        .build();


    var givenPropertyPrice3 = PropertyPrice.builder()
        .sellPrice(520000000L)
        .build();

    var givenPropertyInformation3 = PropertyInformation.builder()
        .area(88500)
        .completionDate(LocalDate.now())
        .moveInDate(LocalDate.now())
        .propertyPrice(givenPropertyPrice3)
        .propertyFloor(new PropertyFloor(2, 2))
        .availableParking(true)
        .residentialType(HOUSING)
        .structureType(MORE_THAN_FOUR_ROOM)
        .contractType(SALE)
        .build();

    Property dummyProperty3 = Property.builder()
        .user(dummyUser)
        .title("대전 주택~~")
        .content("대전 주택이요~~")
        .propertyInformation(givenPropertyInformation3)
        .build();


    var givenPropertyPrice4 = PropertyPrice.builder()
        .deposit(1000000000L)
        .monthlyPrice(2000000)
        .adminPrice(100000)
        .build();

    var givenPropertyInformation4 = PropertyInformation.builder()
        .area(78000)
        .completionDate(LocalDate.now())
        .moveInDate(LocalDate.now())
        .propertyPrice(givenPropertyPrice4)
        .propertyFloor(new PropertyFloor(11, 20))
        .availableParking(true)
        .residentialType(OFFICE)
        .structureType(TWO_ROOM)
        .contractType(MONTHLY_LEASE)
        .build();

    Property dummyProperty4 = Property.builder()
        .user(dummyUser)
        .title("종로구 역세권 오피스텔")
        .content("종로구 역세권 오피스텔 월세 저렴!!")
        .propertyInformation(givenPropertyInformation4)
        .build();



    var givenPropertyPrice5 = PropertyPrice.builder()
        .deposit(800000000L)
        .monthlyPrice(3000000)
        .adminPrice(150000)
        .build();

    var givenPropertyInformation5 = PropertyInformation.builder()
        .area(102000)
        .completionDate(LocalDate.now())
        .moveInDate(LocalDate.now())
        .propertyPrice(givenPropertyPrice5)
        .propertyFloor(new PropertyFloor(2, 3))
        .availableParking(true)
        .residentialType(STORE)
        .structureType(THREE_ROOM)
        .contractType(MONTHLY_LEASE)
        .build();

    Property dummyProperty5 = Property.builder()
        .user(dummyUser)
        .title("마포구 상가 저렴해요!")
        .content("마포구 상가 역세권 저럼합니다")
        .propertyInformation(givenPropertyInformation5)
        .build();


    var givenPropertyPrice6 = PropertyPrice.builder()
        .deposit(50000000L)
        .monthlyPrice(4000000)
        .adminPrice(30000)
        .build();

    var givenPropertyInformation6 = PropertyInformation.builder()
        .area(42000)
        .completionDate(LocalDate.now())
        .moveInDate(LocalDate.now())
        .propertyPrice(givenPropertyPrice6)
        .propertyFloor(new PropertyFloor(6, 6))
        .availableParking(false)
        .residentialType(OFFICE_HOTEL)
        .structureType(DUPLEX)
        .contractType(MONTHLY_LEASE)
        .build();

    Property dummyProperty6 = Property.builder()
        .user(dummyUser)
        .title("금천구 복층 원룸 저렴해요!")
        .content("금천구 복층 원룸 저럼합니다")
        .propertyInformation(givenPropertyInformation6)
        .build();

    return List.of(
        dummyProperty1,
        dummyProperty2,
        dummyProperty3,
        dummyProperty4,
        dummyProperty5,
        dummyProperty6
    );
  }

  protected List<PropertyAddress> initAddress(List<Property> properties) {
    PropertyAddress dummyAddress1 = PropertyAddress.builder()
        .address("서울특별시 서대문구 신촌동 806호")
        .roadAddress("서울특별시 서대문구 xxx로 11가길")
        .city("서울")
        .zipcode("01185")
        .property(properties.get(0))
        .latitude(126.917885535023d)
        .longitude(37.5280674292228d)
        .build();

    PropertyAddress dummyAddress2 = PropertyAddress.builder()
        .address("서울특별시 강남구 역삼동 xx호")
        .roadAddress("서울특별시 강남구 xxx로 22나길")
        .city("서울")
        .zipcode("04285")
        .property(properties.get(1))
        .latitude(631.447885535023d)
        .longitude(327.5280674292228d)
        .build();

    PropertyAddress dummyAddress3 = PropertyAddress.builder()
        .address("대전광역시 서구 도마2동 359-22")
        .roadAddress("대전광역시 배재로 91번길")
        .city("대전")
        .zipcode("35344")
        .property(properties.get(2))
        .latitude(15.917885535023d)
        .longitude(737.52830611292228d)
        .build();

    PropertyAddress dummyAddress4 = PropertyAddress.builder()
        .address("서울특별시 종로구 낙원동 58-1")
        .roadAddress("종로구 삼일대로30길 21")
        .city("서울")
        .zipcode("03132")
        .property(properties.get(3))
        .latitude(21.433385535023d)
        .longitude(77.52830611294428d)
        .build();

    PropertyAddress dummyAddress5 = PropertyAddress.builder()
        .address("서울특별시 마포구 도화동 536")
        .roadAddress("서울특별시 마포구 토정로37길 46")
        .city("서울")
        .zipcode("04157")
        .property(properties.get(4))
        .latitude(341.123385535023d)
        .longitude(123.52830611294428d)
        .build();

    PropertyAddress dummyAddress6 = PropertyAddress.builder()
        .address("서울특별시 금천구 가산동 237-17")
        .roadAddress("서울특별시 금천구 가산동 가산로7길 87")
        .city("서울")
        .zipcode("08519")
        .property(properties.get(5))
        .latitude(31.123385535023d)
        .longitude(23.52830611294428d)
        .build();

    return List.of(
        dummyAddress1,
        dummyAddress2,
        dummyAddress3,
        dummyAddress4,
        dummyAddress5,
        dummyAddress6
    );
  }
}
