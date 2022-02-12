package com.realestate.service.property.entity;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class PropertyPrice {
  /**
   * 매매 금액.
   */
  private Long sellPrice;
  /**
   * 보증금.
   */
  private Long deposit;
  /**
   * 월세.
   */
  private Integer monthlyPrice;
  /**
   * 관리비.
   */
  private Integer adminPrice;

  /**
   * 매물 가격 생성자.
   */
  @Builder
  public PropertyPrice(Long sellPrice,
                       Long deposit,
                       Integer monthlyPrice,
                       Integer adminPrice) {
    this.sellPrice = sellPrice;
    this.deposit = deposit;
    this.monthlyPrice = monthlyPrice;
    this.adminPrice = adminPrice;
  }
}
