package com.realestate.service.property.entity;

import java.util.List;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.realestate.service.property.address.entity.QPropertyAddress;
import com.realestate.service.property.constant.ContractType;
import com.realestate.service.property.constant.KeywordType;
import com.realestate.service.property.constant.ResidentialType;
import com.realestate.service.property.constant.StructureType;
import com.realestate.service.property.dto.FindPropertyQueryDto;
import com.realestate.service.user.entity.QUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class PropertyCustomRepositoryImpl implements PropertyCustomRepository {
  
  private final JPAQueryFactory queryFactory;
  private final QProperty property = QProperty.property;
  private final QUser user = QUser.user;
  private final QPropertyAddress propertyAddress = QPropertyAddress.propertyAddress;

  private static final int FIRST_INDEX = 0;

  @Override
  public Slice<PropertyDataResponse> find(FindPropertyQueryDto queryDto) {
    final var page = queryDto.createPageRequest();
    boolean hasNext = false;

    List<PropertyDataResponse> properties = getPropertyQueryResult(queryDto, page);

    if (queryDto.isSameLimitAs(properties.size())) {
      properties = properties.subList(FIRST_INDEX, page.getPageSize());
      hasNext = true;
    }

    return new SliceImpl<>(properties, page, hasNext);
  }

  private List<PropertyDataResponse> getPropertyQueryResult(FindPropertyQueryDto queryDto,
                                                            PageRequest page) {
    return queryFactory
        .select(
            new QPropertyDataResponse(
                user.id,
                property.id,
                property.title,
                property.content,
                property.propertyInformation.structureType,
                property.propertyInformation.contractType,
                property.propertyInformation.residentialType,
                property.propertyInformation.availableParking,
                property.propertyInformation.propertyPrice.sellPrice,
                property.propertyInformation.propertyPrice.deposit,
                property.propertyInformation.propertyPrice.monthlyPrice,
                property.propertyInformation.propertyPrice.adminPrice,
                property.propertyInformation.propertyFloor.floor,
                property.propertyInformation.propertyFloor.topFloor,
                propertyAddress.city,
                propertyAddress.address,
                propertyAddress.roadAddress,
                propertyAddress.zipcode,
                propertyAddress.latitude,
                propertyAddress.longitude
            )
        )
        .from(property)
        .join(property.user, user)
        .join(propertyAddress)
        .on(property.id.eq(propertyAddress.property.id))
        .where(
            cityEquals(queryDto.getCity()),
            structureTypeEquals(queryDto.getStructureType()),
            residentialTypeEquals(queryDto.getResidentialType()),
            contractTypeEquals(queryDto.getContractType()),
            addressContains(queryDto.getAddress()),
            availableParkingEquals(queryDto.getAvailableParking()),
            keywordSearchEquals(
                queryDto.getKeywordType(),
                queryDto.getSearchText()
            ),
            property.deletedDateTime.isNull()
        )
        .orderBy(property.createdDateTime.desc())
        .offset(page.getOffset())
        .limit(queryDto.getLimit())
        .fetch();
  }

  /**
   * 주차 가능 여부 조회.
   */
  private BooleanExpression availableParkingEquals(Boolean availableParking) {
    return availableParking != null
        ? property.propertyInformation.availableParking.eq(availableParking) : null;
  }

  /**
   * 매물 구조 타입 조회.
   */
  private BooleanExpression structureTypeEquals(StructureType structureType) {
    return structureType != null
        ? property.propertyInformation.structureType.eq(structureType) : null;
  }

  /**
   * 거주 타입 조회.
   */
  private BooleanExpression residentialTypeEquals(ResidentialType residentialType) {
    return residentialType != null
        ? property.propertyInformation.residentialType.eq(residentialType) : null;
  }

  /**
   * 계약 타입 조회.
   */
  private BooleanExpression contractTypeEquals(ContractType contractType) {
    return contractType != null
        ? property.propertyInformation.contractType.eq(contractType) : null;
  }

  /**
   * 주소 검색.
   */
  private BooleanExpression addressContains(String address) {
    if (!StringUtils.hasText(address)) {
      return null;
    }
    return propertyAddress.address.contains(address)
        .or(propertyAddress.roadAddress.contains(address));
  }

  /**
   * 도시 조회.
   */
  private BooleanExpression cityEquals(String city) {
    return StringUtils.hasText(city) ? propertyAddress.city.eq(city) : null;
  }

  /**
   * 키워드 검색.
   */
  private BooleanExpression keywordSearchEquals(KeywordType keywordType, String searchText) {
    if (keywordType == null || !StringUtils.hasText(searchText)) {
      return null;
    }
    switch (keywordType) {
      case TITLE:
        return property.title.contains(searchText);
      case CONTENT:
        return property.content.contains(searchText);
      default:
        throw new AssertionError("property keyword type processing failure");
    }
  }
}
