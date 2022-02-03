package com.realestate.service.member;

import java.util.List;

import com.realestate.service.web.member.response.FindMemberResponse;

public interface FindMemberUseCase {

  List<FindMemberResponse> find(FindMemberCommand command);
}
