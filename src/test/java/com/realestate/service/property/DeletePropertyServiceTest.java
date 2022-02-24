package com.realestate.service.property;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;
import java.util.Optional;

import com.realestate.service.property.entity.Property;
import com.realestate.service.property.entity.PropertyRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@DisplayName("deletePropertyService 테스트")
@ExtendWith(MockitoExtension.class)
class DeletePropertyServiceTest {

  PropertyServiceHelper helper = new PropertyServiceHelper();

  @InjectMocks
  DeletePropertyService deletePropertyService;

  @Mock
  PropertyRepository propertyRepository;

  @BeforeEach
  void setUp() {
    var savedUser = helper.savedUser();
    var savedProperty = helper.createdProperty(savedUser);
    var fakeId = 1L;

    ReflectionTestUtils.setField(savedProperty, "id", fakeId);

    given(propertyRepository.findByIdAndUserId(anyLong(), anyLong()))
        .willReturn(Optional.of(savedProperty));
  }

  @Nested
  @DisplayName("delete 메서드는")
  class DescribeOf_delete {

    @Nested
    @DisplayName("매물 식별자 번호와 유저 식별자가 주어질 경우")
    class ContextWith_deletedPropertyByIdAndUserId {

      @Test
      @DisplayName("매물을 찾은 후 삭제처리 한다.")
      void it_returns() {
        // given
        var givenPropertyId = 1L;
        var givenUserId = 1L;

        // when
        Property deletedProperty = deletePropertyService.delete(givenPropertyId, givenUserId);

        // then
        Assertions.assertThat(deletedProperty).isNotNull();
        Assertions.assertThat(deletedProperty.getId()).isEqualTo(givenPropertyId);
        Assertions.assertThat(deletedProperty.getDeletedDateTime()).isBeforeOrEqualTo(LocalDateTime.now());

      }
    }
  }


}
