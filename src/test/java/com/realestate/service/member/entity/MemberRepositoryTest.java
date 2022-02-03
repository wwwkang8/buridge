package com.realestate.service.member.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import com.realestate.service.config.TestConfig;
import com.realestate.service.member.constant.GenderType;
import com.realestate.service.member.dto.MemberQueryDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@DisplayName("MemberRepository 클래스")
@ActiveProfiles("test")
@Import(TestConfig.class)
@DataJpaTest
class MemberRepositoryTest {

  @Autowired
  MemberRepository memberRepository;

  @BeforeEach
  void setUp() {
    List<Member> members = List.of(
            new Member("손흥민", 30, GenderType.MALE),
            new Member("김연아", 32, GenderType.FEMALE),
            new Member("이강인", 21, GenderType.MALE),
            new Member("김연경", 35, GenderType.FEMALE)
    );
    memberRepository.saveAll(members);
  }

  @Nested
  @DisplayName("find 메소드는")
  class DescribeOf_find {

    @Nested
    @DisplayName("회원 이름과 시작일 종료일이 주어질 경우")
    class ContextWith_memberNameAndDateTimeCondition {

      final String givenMemberName = "이강인";
      final LocalDateTime givenFromDateTime = LocalDateTime.now().minusDays(1L);
      final LocalDateTime givenToDateTime = LocalDateTime.now().plusDays(6L);
      final MemberQueryDto command = new MemberQueryDto(givenMemberName, givenFromDateTime, givenToDateTime);

      final int firstIndex = 0;
      final int resultEqualAge = 21;

      @Test
      @DisplayName("조건에 맞는 회원 목록을 반환한다")
      void it_returns() {

        List<Member> result = memberRepository.find(command);
        log.info(result.toString());
        assertThat(result).isNotNull();
        assertThat(result.get(firstIndex).getAge()).isEqualTo(resultEqualAge);
      }
    }
 }

}