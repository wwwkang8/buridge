package com.realestate.service.web.member;

import java.time.LocalDateTime;
import java.util.List;

import com.realestate.service.member.FindMemberCommand;
import com.realestate.service.member.FindMemberUseCase;
import com.realestate.service.web.member.response.FindMemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberRestController {

  private final FindMemberUseCase findMemberUseCase;

  /**
   * 이름과 조회 시작일로 회원 목록을 조회 합니다.
   *
   * @param name           회원 이름.
   * @param fromDateTime   조회 시작일.
   */
  @GetMapping("/v1/members")
  public List<FindMemberResponse> find(
          @RequestParam(name = "name", required = false) String name,
          @RequestParam(name = "fromDateTime", required = false)
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDateTime) {
    return findMemberUseCase.find(new FindMemberCommand(name, fromDateTime));
  }
}

