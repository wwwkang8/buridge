package com.realestate.service.property.image.entity;

import java.time.LocalDateTime;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.realestate.service.property.entity.Property;
import com.realestate.service.property.image.constant.MimeType;
import com.realestate.service.property.image.converter.MimeTypeConverter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table
@Entity
public class PropertyImage {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "property_id")
  private Property property;
  /**
   * 원본 이미지 명.
   */
  private String originalFileName;
  /**
   * 확장자.
   */
  @Convert(converter = MimeTypeConverter.class)
  private MimeType mimeType;

  private String hash;

  private String path;

  private int size;
  /**
   * 이미지 도메인.
   */
  private String host;
  /**
   * 생성 일시.
   */
  private LocalDateTime createdDateTime;

  /**
   * 매물 이미지 생성자.
   */
  @Builder
  public PropertyImage(Property property,
                       String originalFileName,
                       MimeType mimeType,
                       String hash,
                       String path,
                       int size,
                       String host) {
    this.property = property;
    this.originalFileName = originalFileName;
    this.mimeType = mimeType;
    this.hash = hash;
    this.path = path;
    this.size = size;
    this.host = host;
    addCreatedDateTime();
  }

  private void addCreatedDateTime() {
    this.createdDateTime = LocalDateTime.now();
  }
}
