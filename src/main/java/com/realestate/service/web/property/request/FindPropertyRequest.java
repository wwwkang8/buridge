package com.realestate.service.web.property.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.realestate.service.property.FindPropertyCommand;
import com.realestate.service.property.constant.ContractType;
import com.realestate.service.property.constant.KeywordType;
import com.realestate.service.property.constant.ResidentialType;
import com.realestate.service.property.constant.StructureType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class FindPropertyRequest {
  private final String city;
  private final KeywordType keywordType;
  private final String searchText;
  private final String address;
  private final StructureType structureType;
  private final ContractType contractType;
  private final ResidentialType residentialType;
  private final Boolean availableParking;
  /**
   * 페이지 번호.
   */
  @Min(value = 1, message = "page 값은 1 이상 이어야 합니다.")
  private final int page;

  /**
   * 페이지 당 노출 row 사이즈.
   */
  @Min(value = 1, message = "pageSize 값은 1 이상 이어야 합니다.")
  @Max(value = 1000, message = "pageSize 값은 1,000 이하 이어야 합니다.")
  private final int pageSize;

  /**
   * FindPropertyCommand 객체를 생성하여 리턴합니다.
   */
  public FindPropertyCommand toCommand() {
    return FindPropertyCommand.builder()
        .city(city)
        .keywordType(keywordType)
        .searchText(searchText)
        .address(address)
        .structureType(structureType)
        .contractType(contractType)
        .residentialType(residentialType)
        .availableParking(availableParking)
        .page(page)
        .pageSize(pageSize)
        .build();
  }
}
