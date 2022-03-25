package com.realestate.service.property;

import static com.realestate.service.property.constant.ContractType.*;
import static com.realestate.service.property.constant.ResidentialType.APARTMENT;
import static com.realestate.service.property.constant.StructureType.THREE_ROOM;

import com.realestate.service.property.address.entity.PropertyAddress;
import com.realestate.service.property.entity.PropertyDataResponse;
import com.realestate.service.property.entity.PropertyDetailDataResponse;
import java.time.LocalDate;

import com.realestate.service.property.entity.Property;
import com.realestate.service.property.entity.PropertyFloor;
import com.realestate.service.property.entity.PropertyInformation;
import com.realestate.service.property.entity.PropertyPrice;
import com.realestate.service.user.constant.Role;
import com.realestate.service.user.constant.Status;
import com.realestate.service.user.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

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
  final String givenZipcode = "011290";
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
        .structureType(THREE_ROOM)
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
        .zipcode(givenZipcode)
        .latitude(givenLatitude)
        .longitude(givenLongitude)
        .build();
  }

  protected Slice<PropertyDataResponse> getProperties() {
    List<PropertyDataResponse> contents = List.of(
        new PropertyDataResponse(
            1L,
            1L,
            givenTitle,
            givenContent,
            THREE_ROOM,
            SALE,
            APARTMENT,
            true,
            givenSellPrice,
            givenDeposit,
            givenMonthlyPrice,
            givenAdminPrice,
            givenFloor,
            givenTopFloor,
            givenCity,
            givenAddress,
            givenRoadAddress,
            givenZipcode,
            givenLatitude,
            givenLongitude
        )
    );
    final int givenPage = 1;
    final int givenPageSize = 10;

    return new SliceImpl<>(contents, PageRequest.of(givenPage, givenPageSize), false);

  }

  Optional<PropertyDetailDataResponse> getProperty() {
    return Optional.of(
        new PropertyDetailDataResponse(
            1L,
            givenEmail,
            givenNickName,
            1L,
            givenTitle,
            givenContent,
            200,
            THREE_ROOM,
            SALE,
            APARTMENT,
            true,
            LocalDate.now(),
            LocalDate.now().minusYears(3),
            givenSellPrice,
            givenDeposit,
            givenMonthlyPrice,
            givenAdminPrice,
            givenFloor,
            givenTopFloor,
            givenCity,
            givenAddress,
            givenRoadAddress,
            givenZipcode,
            givenLatitude,
            givenLongitude,
            LocalDateTime.now(),
            LocalDateTime.now()
        )
    );
  }

}
