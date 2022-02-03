package com.realestate.service.web.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.realestate.service.web.member.response.FindMemberResponse;

import java.time.LocalDateTime;
import java.util.List;

public class MemberMockHelper {
  private static final ObjectMapper objectMapper;

  static {
    objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
  }

  /**
   * 회원 목록 조회.
   * @return
   */
  List<FindMemberResponse> getFindMembers() {
    return List.of(
            new FindMemberResponse(1L,"이강인", 23, LocalDateTime.now()),
            new FindMemberResponse(2L,"손흥민", 30, LocalDateTime.now()),
            new FindMemberResponse(3L,"깅연아", 32, LocalDateTime.now())
    );
  }


}
