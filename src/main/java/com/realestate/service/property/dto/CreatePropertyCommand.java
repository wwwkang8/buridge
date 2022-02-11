package com.realestate.service.property.dto;

import java.time.LocalDate;

import com.realestate.service.property.constant.ResidentialType;
import com.realestate.service.property.constant.StructureType;
import com.realestate.service.property.entity.Property;
import com.realestate.service.property.entity.PropertyFloor;
import com.realestate.service.property.entity.PropertyInformation;
import com.realestate.service.property.entity.PropertyPrice;
import com.realestate.service.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreatePropertyCommand {
  private final long userId;
  private final String title;
  private final String content;
  private final int area;
  private final StructureType structureType;
  private final Long sellPrice;
  private final Long deposit;
  private final Integer monthlyPrice;
  private final Integer adminPrice;
  private final ResidentialType residentialType;
  private final int floor;
  private final int topFloor;
  private final Boolean availableParking;
  private final LocalDate moveInDate;
  private final LocalDate estDate;

  /**
   * CreatePropertyCommand 를 생성하여 리턴합니다.
   */
  @Builder
  public CreatePropertyCommand(long userId,
                               String title,
                               String content,
                               int area,
                               StructureType structureType,
                               Long sellPrice,
                               Long deposit,
                               Integer monthlyPrice,
                               Integer adminPrice,
                               ResidentialType residentialType,
                               int floor,
                               int topFloor,
                               Boolean availableParking,
                               LocalDate moveInDate,
                               LocalDate estDate) {
    this.userId = userId;
    this.title = title;
    this.content = content;
    this.area = area;
    this.structureType = structureType;
    this.sellPrice = sellPrice;
    this.deposit = deposit;
    this.monthlyPrice = monthlyPrice;
    this.adminPrice = adminPrice;
    this.residentialType = residentialType;
    this.floor = floor;
    this.topFloor = topFloor;
    this.availableParking = availableParking;
    this.moveInDate = moveInDate;
    this.estDate = estDate;
  }

  /**
   * 매물 엔티티를 반환합니다.
   */
  public Property toEntity(User user) {
    return Property.builder()
        .user(user)
        .title(title)
        .content(content)
        .propertyInformation(createPropertyInformation())
        .build();
  }

  private PropertyInformation createPropertyInformation() {
    return PropertyInformation.builder()
        .propertyPrice(createPropertyPrice())
        .propertyFloor(createPropertyFloor())
        .structureType(structureType)
        .availableParking(availableParking)
        .moveInDate(moveInDate)
        .estDate(estDate)
        .area(area)
        .residentialType(residentialType)
        .build();
  }

  private PropertyPrice createPropertyPrice() {
    return PropertyPrice.builder()
        .adminPrice(adminPrice)
        .monthlyPrice(monthlyPrice)
        .deposit(deposit)
        .sellPrice(sellPrice)
        .build();
  }

  private PropertyFloor createPropertyFloor() {
    return new PropertyFloor(floor, topFloor);
  }
}
