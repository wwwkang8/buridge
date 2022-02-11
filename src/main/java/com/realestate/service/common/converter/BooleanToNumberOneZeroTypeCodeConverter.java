package com.realestate.service.common.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BooleanToNumberOneZeroTypeCodeConverter implements
    AttributeConverter<Boolean, Integer> {

  private static final int TRUE_NUMBER_VALUE = 1;
  private static final int FALSE_NUMBER_VALUE = 0;

  @Override
  public Integer convertToDatabaseColumn(Boolean attribute) {
    return (attribute != null && attribute) ? TRUE_NUMBER_VALUE : FALSE_NUMBER_VALUE;
  }

  @Override
  public Boolean convertToEntityAttribute(Integer number) {
    if (number == null) {
      return false;
    }
    return (TRUE_NUMBER_VALUE == number);
  }
}
