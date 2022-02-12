package com.realestate.service.property;

import static com.realestate.service.property.constant.ResidentialType.APARTMENT;
import static com.realestate.service.property.constant.StructureType.TREE_ROOM;

import java.time.LocalDate;

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
        .estDate(LocalDate.now())
        .moveInDate(LocalDate.now())
        .propertyPrice(givenPropertyPrice)
        .propertyFloor(new PropertyFloor(givenFloor, givenTopFloor))
        .availableParking(true)
        .residentialType(APARTMENT)
        .structureType(TREE_ROOM)
        .build();

    return Property.builder()
        .user(savedUser)
        .title(givenTitle)
        .content(givenContent)
        .propertyInformation(givenPropertyInformation)
        .build();
  }
}
