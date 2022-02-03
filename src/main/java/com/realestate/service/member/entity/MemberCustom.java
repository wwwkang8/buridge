package com.realestate.service.member.entity;

import java.util.List;

import com.realestate.service.member.dto.MemberQueryDto;

public interface MemberCustom {

  List<Member> find(MemberQueryDto memberQueryDto);

}
