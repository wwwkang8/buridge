package com.realestate.service.property;

import static com.realestate.service.property.constant.ContractType.SALE;
import static com.realestate.service.property.constant.KeywordType.*;
import static com.realestate.service.property.constant.ResidentialType.APARTMENT;
import static com.realestate.service.property.constant.StructureType.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.realestate.service.property.address.entity.PropertyAddressRepository;
import com.realestate.service.property.constant.KeywordType;
import com.realestate.service.property.constant.StructureType;
import com.realestate.service.property.dto.FindPropertyQueryDto;
import com.realestate.service.property.entity.PropertyDataResponse;
import com.realestate.service.property.entity.PropertyRepository;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

@DisplayName("FindPropertyService 테스트")
@ExtendWith(MockitoExtension.class)
class FindPropertyServiceTest {

  PropertyServiceHelper helper = new PropertyServiceHelper();

  @InjectMocks
  FindPropertyService findPropertyService;

  @Mock
  PropertyRepository propertyRepository;


  @BeforeEach
  void setUp() {

    final var queryDto = FindPropertyQueryDto.builder()
            .build();
   propertyRepository.find(queryDto);
    given(propertyRepository.find(any()))
        .willReturn(helper.getProperties());
  }


  @Nested
  @DisplayName("find 메서드는")
  class DescribeOf_find {
    @Nested
    @DisplayName("매물 조회 정보가 주어질 경우")
    class ContextWith_findCommand {

      @Test
      @DisplayName("조건에 해당하는 정보를 반환한다.")
      void it_return() {
        // given
        final String givenTitle = "testTitle";
        final String givenCity = "givenCity";
        final String givenAddress = "givenAddress";
        final int givenPage = 1;
        final int givenPageSize = 10;
        final var command = FindPropertyCommand.builder()
            .city(givenCity)
            .address(givenAddress)
            .keywordType(TITLE)
            .searchText(givenTitle)
            .availableParking(true)
            .residentialType(APARTMENT)
            .contractType(SALE)
            .structureType(THREE_ROOM)
            .page(givenPage)
            .pageSize(givenPageSize)
            .build();

        // when
        Slice<PropertyDataResponse> result = findPropertyService.find(command);

        // then
        assertThat(result).isNotNull();
        assertThat(result.hasNext()).isFalse();
        assertThat(result.getPageable().getOffset()).isEqualTo(givenPageSize);
        assertThat(result.getPageable().getPageNumber()).isEqualTo(givenPage);
        assertThat(result.hasContent()).isTrue();
        assertThat(result.getContent().get(0).getUserNumber()).isEqualTo(1L);
        assertThat(result.getContent().get(0).getId()).isEqualTo(1L);
        assertThat(result.getContent().get(0).getCity()).isEqualTo(givenCity);
        assertThat(result.getContent().get(0).getAddress()).isEqualTo(givenAddress);
        assertThat(result.getContent().get(0).getTitle()).isEqualTo(givenTitle);
        assertThat(result.getContent().get(0).getResidentialtype()).isEqualTo(APARTMENT);
        assertThat(result.getContent().get(0).getContractType()).isEqualTo(SALE);
        assertThat(result.getContent().get(0).getStructureType()).isEqualTo(THREE_ROOM);
      }

    }
  }

}
