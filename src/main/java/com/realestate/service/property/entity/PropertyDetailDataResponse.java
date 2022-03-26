package com.realestate.service.property.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;
import com.realestate.service.property.constant.ContractType;
import com.realestate.service.property.constant.ResidentialType;
import com.realestate.service.property.constant.StructureType;
import lombok.Getter;

@Getter
public class PropertyDetailDataResponse {
  private final Long userNumber;
  private final String userEmail;
  private final String nickName;
  private final Long id;
  private final String title;
  private final String content;
  private final int area;
  private final StructureType structureType;
  private final ContractType contractType;
  private final ResidentialType residentialType;
  private final Boolean availableParking;
  private final LocalDate moveInDate;
  private final LocalDate completionDate;
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
  private final LocalDateTime createdDateTime;
  private final LocalDateTime modifiedDateTime;

  /**
   * 생성자.
   */
  @QueryProjection
  public PropertyDetailDataResponse(Long userNumber,
                                    String userEmail,
                                    String nickName,
                                    Long id,
                                    String title,
                                    String content,
                                    int area,
                                    StructureType structureType,
                                    ContractType contractType,
                                    ResidentialType residentialType,
                                    Boolean availableParking,
                                    LocalDate moveInDate,
                                    LocalDate completionDate,
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
                                    double longitude,
                                    LocalDateTime createdDateTime,
                                    LocalDateTime modifiedDateTime) {
    this.userNumber = userNumber;
    this.userEmail = userEmail;
    this.nickName = nickName;
    this.id = id;
    this.title = title;
    this.content = content;
    this.area = area;
    this.structureType = structureType;
    this.contractType = contractType;
    this.residentialType = residentialType;
    this.availableParking = availableParking;
    this.moveInDate = moveInDate;
    this.completionDate = completionDate;
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
    this.createdDateTime = createdDateTime;
    this.modifiedDateTime = modifiedDateTime;
  }
}
