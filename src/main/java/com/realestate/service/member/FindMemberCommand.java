package com.realestate.service.member;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FindMemberCommand {
  private final String name;
  private final LocalDateTime createdDateTime;
}
