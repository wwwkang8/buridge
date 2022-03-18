package com.realestate.service.property.entity;

import com.querydsl.core.annotations.QueryProjection;
import com.realestate.service.property.constant.ContractType;
import com.realestate.service.property.constant.ResidentialType;
import com.realestate.service.property.constant.StructureType;
import lombok.Getter;

@Getter
public class PropertyDataResponse {

  private final Long userNumber;
  private final Long id;
  private final String title;
  private final String content;
  private final StructureType structureType;
  private final ContractType contractType;
  private final ResidentialType residentialtype;
  private final Boolean availableParking;
  private final Long sellPrice;
  private final Long deposit;
  private final Integer monthlyPrice;
  private final Integer adminPrice;
  private final int floor;
  private final int topFloor;
  private final String city;
  private final String address;
  private final String roadAddress;
  private final String zipcode;
  private final double latitude;
  private final double longitude;

  /**
   * 생성자.
   */
  @QueryProjection
  public PropertyDataResponse(Long userNumber,
                              Long id,
                              String title,
                              String content,
                              StructureType structureType,
                              ContractType contractType,
                              ResidentialType residentialtype,
                              Boolean availableParking,
                              Long sellPrice,
                              Long deposit,
                              Integer monthlyPrice,
                              Integer adminPrice,
                              int floor,
                              int topFloor,
                              String city,
                              String address,
                              String roadAddress,
                              String zipcode,
                              double latitude,
                              double longitude) {
    this.userNumber = userNumber;
    this.id = id;
    this.title = title;
    this.content = content;
    this.structureType = structureType;
    this.contractType = contractType;
    this.residentialtype = residentialtype;
    this.availableParking = availableParking;
    this.sellPrice = sellPrice;
    this.deposit = deposit;
    this.monthlyPrice = monthlyPrice;
    this.adminPrice = adminPrice;
    this.floor = floor;
    this.topFloor = topFloor;
    this.city = city;
    this.address = address;
    this.roadAddress = roadAddress;
    this.zipcode = zipcode;
    this.latitude = latitude;
    this.longitude = longitude;
  }
}
