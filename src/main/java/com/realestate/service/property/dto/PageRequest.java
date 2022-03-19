package com.realestate.service.property.dto;

import lombok.Getter;

@Getter
public class PageRequest {
  /**
   * 페이지 번호.
   */
  private final int page;
  /**
   * 페이지당 노출되는 row 사이즈.
   */
  private final int pageSize;

  /**
   * 페이지와 페이지 사이즈로 PageRequest 객체를 생성합니다.
   */
  public org.springframework.data.domain.PageRequest instance() {
    return org.springframework.data.domain.PageRequest
        .of(this.page - 1, this.pageSize);
  }

  /**
   * 생성자.
   */
  public PageRequest(int page,
                     int pageSize) {
    this.page = page;
    this.pageSize = pageSize;
  }
}
