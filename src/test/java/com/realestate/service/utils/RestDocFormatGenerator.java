package com.realestate.service.utils;

import org.springframework.restdocs.snippet.Attributes;

import static org.springframework.restdocs.snippet.Attributes.key;

public interface RestDocFormatGenerator {
  static Attributes.Attribute getDateTimeFormat() {
    return key("format").value("yyyy-MM-dd'T'HH:mm:ss.SSS+09:00");
  }
}
