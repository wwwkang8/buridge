package com.realestate.service.web.property.response;

import java.time.LocalDateTime;

import com.realestate.service.property.entity.Property;
import lombok.Getter;

@Getter
public class DeletePropertyResponse {

  private final long id;
  private final LocalDateTime deletedDateTime;

  protected DeletePropertyResponse(long id, LocalDateTime deletedDateTime) {
    this.id = id;
    this.deletedDateTime = deletedDateTime;
  }

  public static DeletePropertyResponse of(Property property) {
    return new DeletePropertyResponse(property.getId(), property.getDeletedDateTime());
  }
}
