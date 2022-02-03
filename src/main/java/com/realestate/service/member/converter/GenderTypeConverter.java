package com.realestate.service.member.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.realestate.service.member.constant.GenderType;


@Converter
public class GenderTypeConverter implements AttributeConverter<GenderType, String> {

  @Override
  public String convertToDatabaseColumn(GenderType attribute) {
    return attribute.getValue();
  }

  @Override
  public GenderType convertToEntityAttribute(String value) {
    return GenderType.ofValue(value);
  }
}
