package com.realestate.service.property.entity;

import java.time.LocalDate;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.realestate.service.common.converter.BooleanToNumberOneZeroTypeCodeConverter;
import com.realestate.service.property.constant.ContractType;
import com.realestate.service.property.constant.ResidentialType;
import com.realestate.service.property.constant.StructureType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class PropertyInformation {
  /**
   * 면적.
   */
  private int area;
  /**
   * 구조 타입.
   */
  @Enumerated(EnumType.STRING)
  private StructureType structureType;
  /**
   * 거래 타입.
   */
  @Enumerated(EnumType.STRING)
  private ContractType contractType;
  /**
   * 매물 가격 정보.
   */
  @Embedded
  private PropertyPrice propertyPrice;
  /**
   * 거주 타입.
   */
  @Enumerated(EnumType.STRING)
  private ResidentialType residentialType;
  /**
   * 매물 층 정보.
   */
  @Embedded
  private PropertyFloor propertyFloor;
  /**
   * 주차 가능 여부.
   */
  @Convert(converter = BooleanToNumberOneZeroTypeCodeConverter.class)
  private Boolean availableParking;
  /**
   * 입주 가능일자.
   */
  private LocalDate moveInDate;
  /**
   * 준공일자.
   */
  private LocalDate completionDate;

  /**
   * 매물 정보 생성자.
   */
  @Builder
  public PropertyInformation(int area,
                             StructureType structureType,
                             ContractType contractType,
                             PropertyPrice propertyPrice,
                             ResidentialType residentialType,
                             PropertyFloor propertyFloor,
                             boolean availableParking,
                             LocalDate moveInDate,
                             LocalDate completionDate) {
    this.area = area;
    this.structureType = structureType;
    this.contractType = contractType;
    this.propertyPrice = propertyPrice;
    this.residentialType = residentialType;
    this.propertyFloor = propertyFloor;
    this.availableParking = availableParking;
    this.moveInDate = moveInDate;
    this.completionDate = completionDate;
  }

  /**
   * 매물 정보를 업데이트합니다.
   */
  public void update(int area,
                     StructureType structureType,
                     ContractType contractType,
                     PropertyPrice propertyPrice,
                     ResidentialType residentialType,
                     PropertyFloor propertyFloor,
                     boolean availableParking,
                     LocalDate moveInDate,
                     LocalDate completionDate) {
    this.area = area;
    this.structureType = structureType;
    this.contractType = contractType;
    this.propertyPrice = propertyPrice;
    this.residentialType = residentialType;
    this.propertyFloor = propertyFloor;
    this.availableParking = availableParking;
    this.moveInDate = moveInDate;
    this.completionDate = completionDate;
  }
}
