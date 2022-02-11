package com.realestate.service.property.address.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.realestate.service.property.entity.Property;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table
public class PropertyAddress {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "property_id")
  private Property property;
  /**
   * 도시.
   */
  private String city;
  /**
   * 주소.
   */
  private String address;
  /**
   * 도로명 주소.
   */
  private String roadAddress;
  /**
   * 우편번호.
   */
  private String zipcode;
  /**
   * 위도.
   */
  private double latitude;
  /**
   * 경도.
   */
  private double longitude;

  /**
   * 매물 주소 생성자.
   */
  @Builder
  public PropertyAddress(Property property,
                         String city,
                         String address,
                         String roadAddress,
                         String zipcode,
                         double latitude,
                         double longitude) {
    this.property = property;
    this.city = city;
    this.address = address;
    this.roadAddress = roadAddress;
    this.zipcode = zipcode;
    this.latitude = latitude;
    this.longitude = longitude;
  }
}
