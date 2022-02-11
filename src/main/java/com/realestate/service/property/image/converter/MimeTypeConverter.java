package com.realestate.service.property.image.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.realestate.service.property.image.constant.MimeType;

@Converter
public class MimeTypeConverter implements AttributeConverter<MimeType, String> {

  @Override
  public String convertToDatabaseColumn(MimeType attribute) {
    return attribute.getValue();
  }

  @Override
  public MimeType convertToEntityAttribute(String value) {
    return MimeType.ofValue(value);
  }
}
