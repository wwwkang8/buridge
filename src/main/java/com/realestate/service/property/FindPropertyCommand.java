package com.realestate.service.property;

import com.realestate.service.property.constant.ContractType;
import com.realestate.service.property.constant.KeywordType;
import com.realestate.service.property.constant.ResidentialType;
import com.realestate.service.property.constant.StructureType;
import com.realestate.service.property.dto.FindPropertyQueryDto;
import com.realestate.service.property.dto.PageRequest;
import lombok.Builder;

public class FindPropertyCommand {
  private final String city;
  private final KeywordType keywordType;
  private final String searchText;
  private final String address;
  private final StructureType structureType;
  private final ContractType contractType;
  private final ResidentialType residentialType;
  private final Boolean availableParking;
  private final int page;
  private final int pageSize;

  /**
   * 생성자.
   */
  @Builder
  public FindPropertyCommand(String city,
                             KeywordType keywordType,
                             String searchText,
                             String address,
                             StructureType structureType,
                             ContractType contractType,
                             ResidentialType residentialType,
                             Boolean availableParking,
                             int page,
                             int pageSize) {
    this.city = city;
    this.keywordType = keywordType;
    this.searchText = searchText;
    this.address = address;
    this.structureType = structureType;
    this.contractType = contractType;
    this.residentialType = residentialType;
    this.availableParking = availableParking;
    this.page = page;
    this.pageSize = pageSize;
  }

  /**
   * queryDto 객체를 생성 후 반환합니다.
   */
  public FindPropertyQueryDto toQueryDto() {
    return FindPropertyQueryDto.builder()
        .city(city)
        .keywordType(keywordType)
        .searchText(searchText)
        .address(address)
        .structureType(structureType)
        .contractType(contractType)
        .residentialType(residentialType)
        .availableParking(availableParking)
        .pageRequest(new PageRequest(page, pageSize))
        .build();
  }
}
