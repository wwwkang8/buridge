package com.realestate.service.property.dto;

import com.realestate.service.property.constant.ContractType;
import com.realestate.service.property.constant.KeywordType;
import com.realestate.service.property.constant.ResidentialType;
import com.realestate.service.property.constant.StructureType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FindPropertyQueryDto {

  private static final int SLICE_QUERY_LIMIT_PLUS_NUMBER = 1;

  private final String city;
  private final KeywordType keywordType;
  private final String searchText;
  private final String address;
  private final StructureType structureType;
  private final ContractType contractType;
  private final ResidentialType residentialType;
  private final Boolean availableParking;
  private final PageRequest pageRequest;

  /**
   * 생성자.
   */
  @Builder
  public FindPropertyQueryDto(String city,
                              KeywordType keywordType,
                              String searchText,
                              String address,
                              StructureType structureType,
                              ContractType contractType,
                              ResidentialType residentialType,
                              Boolean availableParking,
                              PageRequest pageRequest) {
    this.city = city;
    this.keywordType = keywordType;
    this.searchText = searchText;
    this.address = address;
    this.structureType = structureType;
    this.contractType = contractType;
    this.residentialType = residentialType;
    this.availableParking = availableParking;
    this.pageRequest = pageRequest;
  }

  /**
   * PageRequest 객체를 생성합니다.
   */
  public org.springframework.data.domain.PageRequest createPageRequest() {
    return pageRequest.instance();
  }

  /**
   * limit 를 반환합니다.
   */
  public int getLimit() {
    return pageRequest.getPageSize() + SLICE_QUERY_LIMIT_PLUS_NUMBER;
  }

  /**
   * limit 와 size 가 같을 경우 true 를 반환합니다.
   */
  public boolean isSameLimitAs(int size) {
    return getLimit() == size;
  }
}
