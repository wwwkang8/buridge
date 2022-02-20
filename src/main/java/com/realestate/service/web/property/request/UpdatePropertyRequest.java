package com.realestate.service.web.property.request;

import java.time.LocalDate;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.realestate.service.common.time.DatePattern;
import com.realestate.service.property.constant.ContractType;
import com.realestate.service.property.constant.ResidentialType;
import com.realestate.service.property.constant.StructureType;
import com.realestate.service.property.dto.UpdatePropertyCommand;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdatePropertyRequest {
  @NotBlank(message = "매물 제목은 필수 입력입니다.")
  private String title;
  @NotBlank(message = "매물 내용은 필수 입력입니다.")
  private String content;
  private InformationDto information;
  private AddressDto address;


  @Getter
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class AddressDto {
    @NotBlank(message = "도시는 필수 입력입니다.")
    private String city;
    @NotBlank(message = "주소는 필수 입력입니다.")
    private String address;
    @NotBlank(message = "도로명 주소는 필수 입력입니다.")
    private String roadAddress;
    @NotBlank(message = "우편번호는 필수 입력입니다.")
    private String zipcode;
    private double latitude;
    private double longitude;
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class InformationDto {
    @Min(value = 1)
    private int area;
    @NotNull(message = "매물 구조 타입은 필수 입력입니다.")
    private StructureType structureType;
    @NotNull(message = "매물 계약 타입은 필수 입력입니다.")
    private ContractType contractType;
    private Long sellPrice;
    private Long deposit;
    private Integer monthlyPrice;
    private Integer adminPrice;
    @NotNull(message = "매물 거주 타입은 필수 입력입니다.")
    private ResidentialType residentialType;
    private int floor;
    private int topFloor;
    @NotNull(message = "주차 여부는 필수 입력입니다.")
    private Boolean availableParking;
    @JsonFormat(pattern = DatePattern.DEFAULT_DATE)
    private LocalDate moveInDate;
    @JsonFormat(pattern = DatePattern.DEFAULT_DATE)
    private LocalDate completionDate;
  }


  /**
   * 요청 객체를 UpdatePropertyCommand 로 변환하여 리턴합니다.
   */
  public UpdatePropertyCommand toUpdatePropertyCommand(long userId) {
    var informationCommand = UpdatePropertyCommand
        .PropertyInformationCommand.builder()
        .title(title)
        .content(content)
        .area(information.getArea())
        .structureType(information.getStructureType())
        .contractType(information.getContractType())
        .sellPrice(information.getSellPrice())
        .deposit(information.getDeposit())
        .monthlyPrice(information.getMonthlyPrice())
        .adminPrice(information.getAdminPrice())
        .residentialType(information.getResidentialType())
        .floor(information.getFloor())
        .topFloor(information.getTopFloor())
        .availableParking(information.getAvailableParking())
        .moveInDate(information.getMoveInDate())
        .completionDate(information.getCompletionDate())
        .build();

    var addressCommand = UpdatePropertyCommand
        .PropertyAddressCommand.builder()
        .city(address.getCity())
        .address(address.getAddress())
        .roadAddress(address.getRoadAddress())
        .zipcode(address.getZipcode())
        .latitude(address.getLatitude())
        .longitude(address.getLongitude())
        .build();

    return new UpdatePropertyCommand(userId, informationCommand, addressCommand);
  }
}
