package com.realestate.service.property;

import java.time.LocalDate;

import com.realestate.service.property.constant.ContractType;
import com.realestate.service.property.constant.ResidentialType;
import com.realestate.service.property.constant.StructureType;
import com.realestate.service.property.entity.PropertyFloor;
import com.realestate.service.property.entity.PropertyInformation;
import com.realestate.service.property.entity.PropertyPrice;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdatePropertyCommand {

  private final long userId;
  private final PropertyInformationCommand propertyInformationCommand;
  private final PropertyAddressCommand propertyAddressCommand;

  /**
   * UpdatePropertyCommand 를 생성하여 리턴합니다.
   */
  public UpdatePropertyCommand(long userId,
                               PropertyInformationCommand propertyInformationCommand,
                               PropertyAddressCommand propertyAddressCommand) {
    this.userId = userId;
    this.propertyInformationCommand = propertyInformationCommand;
    this.propertyAddressCommand = propertyAddressCommand;
  }

  @Getter
  public static class PropertyInformationCommand {
    private final String title;
    private final String content;
    private final int area;
    private final StructureType structureType;
    private final ContractType contractType;
    private final Long sellPrice;
    private final Long deposit;
    private final Integer monthlyPrice;
    private final Integer adminPrice;
    private final ResidentialType residentialType;
    private final int floor;
    private final int topFloor;
    private final Boolean availableParking;
    private final LocalDate moveInDate;
    private final LocalDate completionDate;

    /**
     * PropertyInformationCommand 를 생성하여 리턴합니다.
     */
    @Builder
    public PropertyInformationCommand(String title,
                                      String content,
                                      int area,
                                      StructureType structureType,
                                      ContractType contractType,
                                      Long sellPrice,
                                      Long deposit,
                                      Integer monthlyPrice,
                                      Integer adminPrice,
                                      ResidentialType residentialType,
                                      int floor,
                                      int topFloor,
                                      Boolean availableParking,
                                      LocalDate moveInDate,
                                      LocalDate completionDate) {
      this.title = title;
      this.content = content;
      this.area = area;
      this.structureType = structureType;
      this.contractType = contractType;
      this.sellPrice = sellPrice;
      this.deposit = deposit;
      this.monthlyPrice = monthlyPrice;
      this.adminPrice = adminPrice;
      this.residentialType = residentialType;
      this.floor = floor;
      this.topFloor = topFloor;
      this.availableParking = availableParking;
      this.moveInDate = moveInDate;
      this.completionDate = completionDate;
    }
  }

  @Getter
  public static class PropertyAddressCommand {
    private final String city;
    private final String address;
    private final String roadAddress;
    private final String zipcode;
    private final double latitude;
    private final double longitude;

    /**
     * PropertyAddressCommand 를 생성하여 리턴합니다.
     */
    @Builder
    public PropertyAddressCommand(String city,
                                  String address,
                                  String roadAddress,
                                  String zipcode,
                                  double latitude,
                                  double longitude) {
      this.city = city;
      this.address = address;
      this.roadAddress = roadAddress;
      this.zipcode = zipcode;
      this.latitude = latitude;
      this.longitude = longitude;
    }
  }

  /**
   * 매물 정보를 생성 후 반환합니다.
   */
  public PropertyInformation createPropertyInformation() {
    return PropertyInformation.builder()
        .area(propertyInformationCommand.getArea())
        .structureType(propertyInformationCommand.getStructureType())
        .contractType(propertyInformationCommand.getContractType())
        .residentialType(propertyInformationCommand.getResidentialType())
        .availableParking(propertyInformationCommand.getAvailableParking())
        .moveInDate(propertyInformationCommand.getMoveInDate())
        .completionDate(propertyInformationCommand.getCompletionDate())
        .propertyPrice(createPropertyPrice())
        .propertyFloor(createPropertyFloor())
        .build();
  }

  private PropertyPrice createPropertyPrice() {
    return PropertyPrice.builder()
        .sellPrice(propertyInformationCommand.getSellPrice())
        .deposit(propertyInformationCommand.getDeposit())
        .adminPrice(propertyInformationCommand.getAdminPrice())
        .monthlyPrice(propertyInformationCommand.getMonthlyPrice())
        .build();
  }

  private PropertyFloor createPropertyFloor() {
    return new PropertyFloor(propertyInformationCommand.getFloor(),
        propertyInformationCommand.getTopFloor());
  }

}
