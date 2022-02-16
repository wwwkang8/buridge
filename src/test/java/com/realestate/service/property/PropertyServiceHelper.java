package com.realestate.service.property;

import static com.realestate.service.property.constant.ContractType.*;
import static com.realestate.service.property.constant.ResidentialType.APARTMENT;
import static com.realestate.service.property.constant.StructureType.TREE_ROOM;

import com.realestate.service.property.address.entity.PropertyAddress;
import java.time.LocalDate;

import com.realestate.service.property.constant.ContractType;
import com.realestate.service.property.entity.Property;
import com.realestate.service.property.entity.PropertyFloor;
import com.realestate.service.property.entity.PropertyInformation;
import com.realestate.service.property.entity.PropertyPrice;
import com.realestate.service.user.constant.Role;
import com.realestate.service.user.constant.Status;
import com.realestate.service.user.entity.User;

public class PropertyServiceHelper {
  final String givenEmail = "test123@naver.com";
  final String givenPassword = "12341234";
  final String givenNickName = "givenNickName";

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

  protected User savedUser() {
    return User.createUser(
        givenEmail,
        givenPassword,
        givenNickName,
        Status.ACTIVE,
        Role.NORMAL);
  }

  protected Property createdProperty(User savedUser) {
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
        .contractType(SALE)
        .build();

    return Property.builder()
        .user(savedUser)
        .title(givenTitle)
        .content(givenContent)
        .propertyInformation(givenPropertyInformation)
        .build();
  }

  protected PropertyAddress createdPropertyAddress(Property property) {
    return PropertyAddress.builder()
        .property(property)
        .city(givenCity)
        .address(givenAddress)
        .roadAddress(givenRoadAddress)
        .latitude(givenLatitude)
        .longitude(givenLongitude)
        .build();
  }

}
