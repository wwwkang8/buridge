package com.realestate.service.web.property;

import static com.realestate.service.property.constant.ContractType.*;
import static com.realestate.service.property.constant.ResidentialType.APARTMENT;
import static com.realestate.service.property.constant.StructureType.THREE_ROOM;
import static org.springframework.util.ResourceUtils.CLASSPATH_URL_PREFIX;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.realestate.service.property.entity.Property;
import com.realestate.service.property.entity.PropertyFloor;
import com.realestate.service.property.entity.PropertyInformation;
import com.realestate.service.property.entity.PropertyPrice;
import com.realestate.service.user.constant.Role;
import com.realestate.service.user.constant.Status;
import com.realestate.service.user.entity.User;
import com.realestate.service.web.property.request.CreatePropertyRequest;
import com.realestate.service.web.property.request.UpdatePropertyRequest;
import com.realestate.service.web.property.response.CreatePropertyResponse;
import com.realestate.service.web.property.response.UpdatePropertyResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.util.ResourceUtils;

public class PropertyMockHelper {

  private static final ObjectMapper objectMapper;

  static {
    objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
  }

  final String givenEmail = "test123@naver.com";
  final String givenPassword = "12341234";
  final String givenNickName = "givenNickName";

  final long givenSellPrice = 400000000L;
  final long givenDeposit = 20000000L;
  final int givenAdminPrice = 300000;
  final int givenMonthlyPrice = 200000;
  final int givenArea = 200000;
  final int givenFloor = 1;
  final int givenTopFloor = 4;
  final String givenTitle = "createTestSubject";
  final String givenContent = "createTestContents";
  final String givenUpdatedTitle = "updateTitle";
  final String givenUpdatedContent = "updateContent";

  protected User savedUser() {
    return User.createUser(
        givenEmail,
        givenPassword,
        givenNickName,
        Status.ACTIVE,
        Role.NORMAL);
  }

  protected Property createdProperty() {
    var givenPropertyPrice = PropertyPrice.builder()
        .sellPrice(givenSellPrice)
        .deposit(givenDeposit)
        .adminPrice(givenAdminPrice)
        .monthlyPrice(givenMonthlyPrice)
        .build();

    var givenPropertyInformation = PropertyInformation.builder()
        .area(givenArea)
        .completionDate(LocalDate.now())
        .moveInDate(LocalDate.now())
        .propertyPrice(givenPropertyPrice)
        .propertyFloor(new PropertyFloor(givenFloor, givenTopFloor))
        .availableParking(true)
        .residentialType(APARTMENT)
        .structureType(THREE_ROOM)
        .contractType(JEONSE)
        .build();

    return Property.builder()
        .user(savedUser())
        .title(givenTitle)
        .content(givenContent)
        .propertyInformation(givenPropertyInformation)
        .build();
  }

  protected String getCreatePropertyRequest() throws IOException {
    return objectMapper.writeValueAsString(
        objectMapper.readValue(
            ResourceUtils.getFile(CLASSPATH_URL_PREFIX +
                "mock/property/create_property_request.json"), CreatePropertyRequest.class));
  }

  protected CreatePropertyResponse getCreatePropertyResponse() {
    var givenId = 1L;
    return new CreatePropertyResponseTestDto(
        givenId,
        givenTitle,
        givenContent,
        LocalDateTime.now()
    );
  }

  static class CreatePropertyResponseTestDto extends CreatePropertyResponse {
    public CreatePropertyResponseTestDto(long id,
                                         String title,
                                         String content,
                                         LocalDateTime createdDateTime) {
      super(id, title, content, createdDateTime);
    }
  }

  protected Property updatedProperty() {
    var updatedProperty = createdProperty();

    var givenPropertyPrice = PropertyPrice.builder()
        .sellPrice(givenSellPrice)
        .deposit(givenDeposit)
        .adminPrice(givenAdminPrice)
        .monthlyPrice(givenMonthlyPrice)
        .build();

    var givenPropertyInformation = PropertyInformation.builder()
        .area(givenArea)
        .completionDate(LocalDate.now())
        .moveInDate(LocalDate.now())
        .propertyPrice(givenPropertyPrice)
        .propertyFloor(new PropertyFloor(givenFloor, givenTopFloor))
        .availableParking(true)
        .residentialType(APARTMENT)
        .structureType(THREE_ROOM)
        .contractType(JEONSE)
        .build();

    updatedProperty.update(givenUpdatedTitle, givenUpdatedContent, givenPropertyInformation);

    return updatedProperty;
  }

  protected String getUpdatePropertyRequest() throws IOException {
    return objectMapper.writeValueAsString(
        objectMapper.readValue(
            ResourceUtils.getFile(CLASSPATH_URL_PREFIX +
                "mock/property/update_property_request.json"), UpdatePropertyRequest.class));
  }

  protected UpdatePropertyResponse getUpdatePropertyResponse() {
    var givenId = 1L;
    return new UpdatePropertyResponseTestDto(
        givenId,
        givenUpdatedTitle,
        givenUpdatedContent,
        LocalDateTime.now()
    );
  }

  static class UpdatePropertyResponseTestDto extends UpdatePropertyResponse {
    public UpdatePropertyResponseTestDto(long id,
                                         String title,
                                         String content,
                                         LocalDateTime modifiedDateTime) {
      super(id, title, content, modifiedDateTime);
    }
  }

}
