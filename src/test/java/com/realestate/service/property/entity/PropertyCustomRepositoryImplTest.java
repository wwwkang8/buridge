package com.realestate.service.property.entity;

import static com.realestate.service.property.constant.ContractType.*;
import static com.realestate.service.property.constant.KeywordType.*;
import static com.realestate.service.property.constant.ResidentialType.APARTMENT;
import static com.realestate.service.property.constant.ResidentialType.HOUSING;
import static com.realestate.service.property.constant.ResidentialType.OFFICE;
import static com.realestate.service.property.constant.ResidentialType.OFFICE_HOTEL;
import static com.realestate.service.property.constant.ResidentialType.STORE;
import static com.realestate.service.property.constant.StructureType.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.realestate.service.config.TestConfig;
import com.realestate.service.property.address.entity.PropertyAddressRepository;
import com.realestate.service.property.dto.FindPropertyQueryDto;
import com.realestate.service.property.dto.PageRequest;
import com.realestate.service.property.image.entity.PropertyImageRepository;
import com.realestate.service.user.entity.User;
import com.realestate.service.user.repository.UserRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.ActiveProfiles;

@DisplayName("PropertyCustomRepositoryImpl 테스트")
@ActiveProfiles("test")
@Import(TestConfig.class)
@DataJpaTest
class PropertyCustomRepositoryImplTest {

  @Autowired
  UserRepository userRepository;
  @Autowired
  PropertyRepository propertyRepository;
  @Autowired
  PropertyAddressRepository propertyAddressRepository;
  @Autowired
  PropertyImageRepository propertyImageRepository;

  PropertyCustomRepositoryImplHelper helper = new PropertyCustomRepositoryImplHelper();

  @BeforeEach
  void setUp() {
    User dummyUser = userRepository.save(helper.initUser());
    List<Property> properties = propertyRepository.saveAll(helper.initProperty(dummyUser));
    propertyAddressRepository.saveAll(helper.initAddress(properties));
  }

  @AfterEach
  void after() {
    propertyRepository.deleteAll();
    propertyAddressRepository.deleteAll();
    userRepository.deleteAll();
  }

  @Nested
  @DisplayName("find 메서드는")
  class DescribeOf_find {

    @Nested
    @DisplayName("페이징 조건이 주어질 경우")
    class ContextWith_pagingCondition {

      @Test
      @DisplayName("조건에 해당하는 정보와 페이징 정보를 반환한다.")
      void it_returns() {

        // given
        final int givenPage = 1;
        final int givenPageSize = 20;
        final int resultContentSize = 6;
        final FindPropertyQueryDto queryDto = FindPropertyQueryDto.builder()
            .pageRequest(new PageRequest(givenPage, givenPageSize))
            .build();

        // when
        Slice<PropertyDataResponse> result = propertyRepository.find(queryDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getSize()).isEqualTo(givenPageSize);
        assertThat(result.getContent().size()).isEqualTo(resultContentSize);
        assertThat(result.hasNext()).isFalse();
      }
    }

    @Nested
    @DisplayName("페이징 조건과 도시 명 조건이 주어질 경우")
    class ContextWith_pagingAndCityCondition {

      @Test
      @DisplayName("조건에 해당하는 정보와 페이징 정보를 반환한다.")
      void it_returns() {

        // given
        final String givenCity = "서울";
        final int givenPage = 1;
        final int givenPageSize = 10;
        final int resultContentSize = 5;
        final FindPropertyQueryDto queryDto = FindPropertyQueryDto.builder()
            .city(givenCity)
            .pageRequest(new PageRequest(givenPage, givenPageSize))
            .build();

        // when
        Slice<PropertyDataResponse> result = propertyRepository.find(queryDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getContent().get(0).getCity()).isEqualTo(givenCity);
        assertThat(result.getSize()).isEqualTo(givenPageSize);
        assertThat(result.getContent().size()).isEqualTo(resultContentSize);
      }

    }

    @Nested
    @DisplayName("주어진 매물 구조 타입이")
    class ContextWith_structureType {

      @Nested
      @DisplayName("원 룸일 경우")
      class ContextWith_oneRoomCondition {

        @Test
        @DisplayName("조건에 해당 하는 정보를 반환한다.")
        void it_returns() {

          // given
          final int givenPage = 1;
          final int givenPageSize = 10;
          final int resultContentSize = 1;
          final FindPropertyQueryDto queryDto = FindPropertyQueryDto.builder()
              .structureType(ONE_ROOM)
              .pageRequest(new PageRequest(givenPage, givenPageSize))
              .build();

          // when
          Slice<PropertyDataResponse> result = propertyRepository.find(queryDto);

          // then
          assertThat(result).isNotNull();
          assertThat(result.getContent().get(0).getStructureType()).isEqualTo(ONE_ROOM);
          assertThat(result.getSize()).isEqualTo(givenPageSize);
          assertThat(result.getContent().size()).isEqualTo(resultContentSize);
        }
      }

      @Nested
      @DisplayName("투 룸일 경우")
      class ContextWith_twoRoomCondition {

        @Test
        @DisplayName("조건에 해당 하는 정보를 반환한다.")
        void it_returns() {

          // given
          final int givenPage = 1;
          final int givenPageSize = 10;
          final int resultContentSize = 1;
          final FindPropertyQueryDto queryDto = FindPropertyQueryDto.builder()
              .structureType(TWO_ROOM)
              .pageRequest(new PageRequest(givenPage, givenPageSize))
              .build();

          // when
          Slice<PropertyDataResponse> result = propertyRepository.find(queryDto);

          // then
          assertThat(result).isNotNull();
          assertThat(result.getContent().get(0).getStructureType()).isEqualTo(TWO_ROOM);
          assertThat(result.getSize()).isEqualTo(givenPageSize);
          assertThat(result.getContent().size()).isEqualTo(resultContentSize);
        }
      }

      @Nested
      @DisplayName("쓰리 룸일 경우")
      class ContextWith_threeRoomCondition {

        @Test
        @DisplayName("조건에 해당 하는 정보를 반환한다.")
        void it_returns() {

          // given
          final int givenPage = 1;
          final int givenPageSize = 10;
          final int resultContentSize = 2;
          final FindPropertyQueryDto queryDto = FindPropertyQueryDto.builder()
              .structureType(THREE_ROOM)
              .pageRequest(new PageRequest(givenPage, givenPageSize))
              .build();

          // when
          Slice<PropertyDataResponse> result = propertyRepository.find(queryDto);

          // then
          assertThat(result).isNotNull();
          assertThat(result.getContent().get(0).getStructureType()).isEqualTo(THREE_ROOM);
          assertThat(result.getSize()).isEqualTo(givenPageSize);
          assertThat(result.getContent().size()).isEqualTo(resultContentSize);
        }
      }

      @Nested
      @DisplayName("포 룸 이상일 경우")
      class ContextWith_moreThanFourRoomCondition {

        @Test
        @DisplayName("조건에 해당 하는 정보를 반환한다.")
        void it_returns() {

          // given
          final int givenPage = 1;
          final int givenPageSize = 10;
          final int resultContentSize = 1;
          final FindPropertyQueryDto queryDto = FindPropertyQueryDto.builder()
              .structureType(MORE_THAN_FOUR_ROOM)
              .pageRequest(new PageRequest(givenPage, givenPageSize))
              .build();

          // when
          Slice<PropertyDataResponse> result = propertyRepository.find(queryDto);

          // then
          assertThat(result).isNotNull();
          assertThat(result.getContent().get(0).getStructureType()).isEqualTo(MORE_THAN_FOUR_ROOM);
          assertThat(result.getSize()).isEqualTo(givenPageSize);
          assertThat(result.getContent().size()).isEqualTo(resultContentSize);
        }
      }

      @Nested
      @DisplayName("복 층 이상일 경우")
      class ContextWith_duplexCondition {

        @Test
        @DisplayName("조건에 해당 하는 정보를 반환한다.")
        void it_returns() {

          // given
          final int givenPage = 1;
          final int givenPageSize = 10;
          final int resultContentSize = 1;
          final FindPropertyQueryDto queryDto = FindPropertyQueryDto.builder()
              .structureType(DUPLEX)
              .pageRequest(new PageRequest(givenPage, givenPageSize))
              .build();

          // when
          Slice<PropertyDataResponse> result = propertyRepository.find(queryDto);

          // then
          assertThat(result).isNotNull();
          assertThat(result.getContent().get(0).getStructureType()).isEqualTo(DUPLEX);
          assertThat(result.getSize()).isEqualTo(givenPageSize);
          assertThat(result.getContent().size()).isEqualTo(resultContentSize);
        }
      }

    }

    @Nested
    @DisplayName("주어진 거주 타입이")
    class ContextWith_residentialType {

      @Nested
      @DisplayName("주택일 경우")
      class ContextWith_housingCondition {

        @Test
        @DisplayName("조건에 해당 하는 정보를 반환한다.")
        void it_returns() {

          // given
          final int givenPage = 1;
          final int givenPageSize = 10;
          final int resultContentSize = 1;
          final FindPropertyQueryDto queryDto = FindPropertyQueryDto.builder()
              .residentialType(HOUSING)
              .pageRequest(new PageRequest(givenPage, givenPageSize))
              .build();

          // when
          Slice<PropertyDataResponse> result = propertyRepository.find(queryDto);

          // then
          assertThat(result).isNotNull();
          assertThat(result.getContent().get(0).getResidentialtype()).isEqualTo(HOUSING);
          assertThat(result.getSize()).isEqualTo(givenPageSize);
          assertThat(result.getContent().size()).isEqualTo(resultContentSize);
        }
      }

      @Nested
      @DisplayName("오피스텔일 경우")
      class ContextWith_officeHotelCondition {

        @Test
        @DisplayName("조건에 해당 하는 정보를 반환한다.")
        void it_returns() {

          // given
          final int givenPage = 1;
          final int givenPageSize = 10;
          final int resultContentSize = 2;
          final FindPropertyQueryDto queryDto = FindPropertyQueryDto.builder()
              .residentialType(OFFICE_HOTEL)
              .pageRequest(new PageRequest(givenPage, givenPageSize))
              .build();

          // when
          Slice<PropertyDataResponse> result = propertyRepository.find(queryDto);

          // then
          assertThat(result).isNotNull();
          assertThat(result.getContent().get(0).getResidentialtype()).isEqualTo(OFFICE_HOTEL);
          assertThat(result.getSize()).isEqualTo(givenPageSize);
          assertThat(result.getContent().size()).isEqualTo(resultContentSize);
        }
      }

      @Nested
      @DisplayName("아파트일 경우")
      class ContextWith_apartmentCondition {

        @Test
        @DisplayName("조건에 해당 하는 정보를 반환한다.")
        void it_returns() {

          // given
          final int givenPage = 1;
          final int givenPageSize = 10;
          final int resultContentSize = 1;
          final FindPropertyQueryDto queryDto = FindPropertyQueryDto.builder()
              .residentialType(APARTMENT)
              .pageRequest(new PageRequest(givenPage, givenPageSize))
              .build();

          // when
          Slice<PropertyDataResponse> result = propertyRepository.find(queryDto);

          // then
          assertThat(result).isNotNull();
          assertThat(result.getContent().get(0).getResidentialtype()).isEqualTo(APARTMENT);
          assertThat(result.getSize()).isEqualTo(givenPageSize);
          assertThat(result.getContent().size()).isEqualTo(resultContentSize);
        }
      }

      @Nested
      @DisplayName("사무실일 경우")
      class ContextWith_officeCondition {

        @Test
        @DisplayName("조건에 해당 하는 정보를 반환한다.")
        void it_returns() {

          // given
          final int givenPage = 1;
          final int givenPageSize = 10;
          final int resultContentSize = 1;
          final FindPropertyQueryDto queryDto = FindPropertyQueryDto.builder()
              .residentialType(OFFICE)
              .pageRequest(new PageRequest(givenPage, givenPageSize))
              .build();

          // when
          Slice<PropertyDataResponse> result = propertyRepository.find(queryDto);

          // then
          assertThat(result).isNotNull();
          assertThat(result.getContent().get(0).getResidentialtype()).isEqualTo(OFFICE);
          assertThat(result.getSize()).isEqualTo(givenPageSize);
          assertThat(result.getContent().size()).isEqualTo(resultContentSize);
        }
      }

      @Nested
      @DisplayName("상가일 경우")
      class ContextWith_storeCondition {

        @Test
        @DisplayName("조건에 해당 하는 정보를 반환한다.")
        void it_returns() {

          // given
          final int givenPage = 1;
          final int givenPageSize = 10;
          final int resultContentSize = 1;
          final FindPropertyQueryDto queryDto = FindPropertyQueryDto.builder()
              .residentialType(STORE)
              .pageRequest(new PageRequest(givenPage, givenPageSize))
              .build();

          // when
          Slice<PropertyDataResponse> result = propertyRepository.find(queryDto);

          // then
          assertThat(result).isNotNull();
          assertThat(result.getContent().get(0).getResidentialtype()).isEqualTo(STORE);
          assertThat(result.getSize()).isEqualTo(givenPageSize);
          assertThat(result.getContent().size()).isEqualTo(resultContentSize);
        }
      }

    }

    @Nested
    @DisplayName("주어진 계약 타입이")
    class ContextWith_contractType {

      @Nested
      @DisplayName("매매일 경우")
      class ContextWith_saleCondition {

        @Test
        @DisplayName("조건에 해당 하는 정보를 반환한다.")
        void it_returns() {

          // given
          final int givenPage = 1;
          final int givenPageSize = 10;
          final int resultContentSize = 1;
          final FindPropertyQueryDto queryDto = FindPropertyQueryDto.builder()
              .contractType(SALE)
              .pageRequest(new PageRequest(givenPage, givenPageSize))
              .build();

          // when
          Slice<PropertyDataResponse> result = propertyRepository.find(queryDto);

          // then
          assertThat(result).isNotNull();
          assertThat(result.getContent().get(0).getContractType()).isEqualTo(SALE);
          assertThat(result.getSize()).isEqualTo(givenPageSize);
          assertThat(result.getContent().size()).isEqualTo(resultContentSize);
        }
      }

      @Nested
      @DisplayName("전세일 경우")
      class ContextWith_jeonseCondition {

        @Test
        @DisplayName("조건에 해당 하는 정보를 반환한다.")
        void it_returns() {

          // given
          final int givenPage = 1;
          final int givenPageSize = 10;
          final int resultContentSize = 1;
          final FindPropertyQueryDto queryDto = FindPropertyQueryDto.builder()
              .contractType(JEONSE)
              .pageRequest(new PageRequest(givenPage, givenPageSize))
              .build();

          // when
          Slice<PropertyDataResponse> result = propertyRepository.find(queryDto);

          // then
          assertThat(result).isNotNull();
          assertThat(result.getContent().get(0).getContractType()).isEqualTo(JEONSE);
          assertThat(result.getSize()).isEqualTo(givenPageSize);
          assertThat(result.getContent().size()).isEqualTo(resultContentSize);
        }
      }

      @Nested
      @DisplayName("월세일 경우")
      class ContextWith_monthlyLeaseCondition {

        @Test
        @DisplayName("조건에 해당 하는 정보를 반환한다.")
        void it_returns() {

          // given
          final int givenPage = 1;
          final int givenPageSize = 10;
          final int resultContentSize = 4;
          final FindPropertyQueryDto queryDto = FindPropertyQueryDto.builder()
              .contractType(MONTHLY_LEASE)
              .pageRequest(new PageRequest(givenPage, givenPageSize))
              .build();

          // when
          Slice<PropertyDataResponse> result = propertyRepository.find(queryDto);

          // then
          assertThat(result).isNotNull();
          assertThat(result.getContent().get(0).getContractType()).isEqualTo(MONTHLY_LEASE);
          assertThat(result.getSize()).isEqualTo(givenPageSize);
          assertThat(result.getContent().size()).isEqualTo(resultContentSize);
        }
      }

    }

    @Nested
    @DisplayName("페이징 조건과 주소 조건이 주어질 경우")
    class ContextWith_pagingAndAddressCondition {

      @Test
      @DisplayName("조건에 해당하는 정보와 페이징 정보를 반환한다.")
      void it_returns() {

        // given
        final String givenAddress = "서울특별시 금천구 가산동";
        final int givenPage = 1;
        final int givenPageSize = 10;
        final int resultContentSize = 1;
        final String resultContentAddress = "서울특별시 금천구 가산동 237-17";
        final FindPropertyQueryDto queryDto = FindPropertyQueryDto.builder()
            .address(givenAddress)
            .pageRequest(new PageRequest(givenPage, givenPageSize))
            .build();

        // when
        Slice<PropertyDataResponse> result = propertyRepository.find(queryDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getContent().get(0).getAddress()).isEqualTo(resultContentAddress);
        assertThat(result.getSize()).isEqualTo(givenPageSize);
        assertThat(result.getContent().size()).isEqualTo(resultContentSize);
      }

    }

    @Nested
    @DisplayName("페이징 조건과 주차 가능 여부가")
    class ContextWith_pagingAndAvailableParkingCondition {

      @Nested
      @DisplayName("true일 경우")
      class ContextWith_isTrue {

        @Test
        @DisplayName("조건에 해당하는 정보와 페이징 정보를 반환한다.")
        void it_returns() {

          // given
          final int givenPage = 1;
          final int givenPageSize = 10;
          final int resultContentSize = 4;
          final FindPropertyQueryDto queryDto = FindPropertyQueryDto.builder()
              .availableParking(true)
              .pageRequest(new PageRequest(givenPage, givenPageSize))
              .build();

          // when
          Slice<PropertyDataResponse> result = propertyRepository.find(queryDto);

          // then
          assertThat(result).isNotNull();
          assertThat(result.getContent().get(0).getAvailableParking()).isTrue();
          assertThat(result.getSize()).isEqualTo(givenPageSize);
          assertThat(result.getContent().size()).isEqualTo(resultContentSize);
        }
      }

      @Nested
      @DisplayName("false일 경우")
      class ContextWith_isFalse {

        @Test
        @DisplayName("조건에 해당하는 정보와 페이징 정보를 반환한다.")
        void it_returns() {

          // given
          final int givenPage = 1;
          final int givenPageSize = 10;
          final int resultContentSize = 2;
          final FindPropertyQueryDto queryDto = FindPropertyQueryDto.builder()
              .availableParking(false)
              .pageRequest(new PageRequest(givenPage, givenPageSize))
              .build();

          // when
          Slice<PropertyDataResponse> result = propertyRepository.find(queryDto);

          // then
          assertThat(result).isNotNull();
          assertThat(result.getContent().get(0).getAvailableParking()).isFalse();
          assertThat(result.getSize()).isEqualTo(givenPageSize);
          assertThat(result.getContent().size()).isEqualTo(resultContentSize);
        }
      }

    }

    @Nested
    @DisplayName("주어진 키워드 조건이")
    class ContextWith_keyword {

      @Nested
      @DisplayName("제목일 경우")
      class ContextWith_titleCondition {

        @Test
        @DisplayName("조건에 해당하는 정보를 반환한다.")
        void it_returns() {

          // given
          final String givenTitle = "서대문구";
          final int givenPage = 1;
          final int givenPageSize = 10;
          final int resultContentSize = 1;
          final String resultContentTitle = "서대문구 전세";
          final FindPropertyQueryDto queryDto = FindPropertyQueryDto.builder()
              .keywordType(TITLE)
              .searchText(givenTitle)
              .pageRequest(new PageRequest(givenPage, givenPageSize))
              .build();

          // when
          Slice<PropertyDataResponse> result = propertyRepository.find(queryDto);

          // then
          assertThat(result).isNotNull();
          assertThat(result.getContent().get(0).getTitle()).isEqualTo(resultContentTitle);
          assertThat(result.getSize()).isEqualTo(givenPageSize);
          assertThat(result.getContent().size()).isEqualTo(resultContentSize);
        }
      }

      @Nested
      @DisplayName("내용일 경우")
      class ContextWith_contentCondition {

        @Test
        @DisplayName("조건에 해당하는 정보를 반환한다.")
        void it_returns() {

          // given
          final String giveContent = "오피스텔";
          final int givenPage = 1;
          final int givenPageSize = 10;
          final int resultContentSize = 1;
          final String resultContentContents = "종로구 역세권 오피스텔 월세 저렴!!";
          final FindPropertyQueryDto queryDto = FindPropertyQueryDto.builder()
              .keywordType(CONTENT)
              .searchText(giveContent)
              .pageRequest(new PageRequest(givenPage, givenPageSize))
              .build();

          // when
          Slice<PropertyDataResponse> result = propertyRepository.find(queryDto);

          // then
          assertThat(result).isNotNull();
          assertThat(result.getContent().get(0).getContent()).isEqualTo(resultContentContents);
          assertThat(result.getSize()).isEqualTo(givenPageSize);
          assertThat(result.getContent().size()).isEqualTo(resultContentSize);
        }
      }

    }
  }
}
