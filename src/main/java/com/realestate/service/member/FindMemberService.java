package com.realestate.service.member;

import java.util.List;
import java.util.stream.Collectors;

import com.realestate.service.member.dto.MemberQueryDto;
import com.realestate.service.member.entity.MemberRepository;
import com.realestate.service.web.member.response.FindMemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FindMemberService implements FindMemberUseCase {

  private static final int EXPIRE_DAY = 7;

  private final MemberRepository memberRepository;

  @Transactional(readOnly = true)
  @Override
  public List<FindMemberResponse> find(FindMemberCommand command) {
    return memberRepository.find(new MemberQueryDto(
                    command.getName(),
                    command.getCreatedDateTime(),
                    command.getCreatedDateTime().plusDays(EXPIRE_DAY)))
            .stream()
            .map(FindMemberResponse::new)
            .collect(Collectors.toList());
  }
}
