package com.realestate.service.web.property.response;

import java.time.LocalDateTime;

import com.realestate.service.property.entity.Property;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreatePropertyResponse {
  private final long id;
  private final String title;
  private final String content;
  private final LocalDateTime createdDateTime;

  @Builder
  protected CreatePropertyResponse(long id,
                                String title,
                                String content,
                                LocalDateTime createdDateTime) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.createdDateTime = createdDateTime;
  }

  /**
   * CreatePropertyResponse 를 생성한 후 반환합니다.
   */
  public static CreatePropertyResponse of(Property property) {
    return CreatePropertyResponse.builder()
        .id(property.getId())
        .title(property.getTitle())
        .content(property.getContent())
        .createdDateTime(property.getCreatedDateTime())
        .build();
  }
}
