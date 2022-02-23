package com.realestate.service.property.entity;

import java.time.LocalDateTime;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.realestate.service.common.entity.BaseDateTimeEntity;
import com.realestate.service.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@DynamicUpdate
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table
@Entity
public class Property extends BaseDateTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;
  /**
   * 매물 정보.
   */
  @Embedded
  private PropertyInformation propertyInformation;
  /**
   * 제목.
   */
  private String title;
  /**
   * 내용.
   */
  private String content;
  /**
   * 삭제 일시.
   */
  private LocalDateTime deletedDateTime;

  /**
   * 매물 생성자.
   */
  @Builder
  public Property(User user,
                  String title,
                  String content,
                  PropertyInformation propertyInformation) {
    this.user = user;
    this.title = title;
    this.content = content;
    this.propertyInformation = propertyInformation;
  }

  /**
   * 매물 정보를 업데이트 합니다.
   */
  public void update(String title,
                     String content,
                     PropertyInformation propertyInformation) {
    this.title = title;
    this.content = content;
    this.propertyInformation = propertyInformation;
  }

  public void delete() {
    this.deletedDateTime = LocalDateTime.now();
  }
}
