package com.realestate.service.web.property.response;

import java.time.LocalDateTime;

import com.realestate.service.property.entity.Property;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdatePropertyResponse {
  private final long id;
  private final String title;
  private final String content;
  private final LocalDateTime modifiedDateTime;

  @Builder
  protected UpdatePropertyResponse(long id,
                                   String title,
                                   String content,
                                   LocalDateTime modifiedDateTime) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.modifiedDateTime = modifiedDateTime;
  }

  /**
   * UpdatePropertyResponse 를 생성한 후 반환합니다.
   */
  public static UpdatePropertyResponse of(Property property) {
    return UpdatePropertyResponse.builder()
        .id(property.getId())
        .title(property.getTitle())
        .content(property.getContent())
        .modifiedDateTime(property.getModifiedDateTime())
        .build();
  }
}
