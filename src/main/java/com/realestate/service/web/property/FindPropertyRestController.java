package com.realestate.service.web.property;

import javax.validation.Valid;

import com.realestate.service.property.FindPropertyUseCase;
import com.realestate.service.property.entity.PropertyDataResponse;
import com.realestate.service.property.entity.PropertyDetailDataResponse;
import com.realestate.service.web.property.request.FindPropertyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class FindPropertyRestController {

  private final FindPropertyUseCase findPropertyUseCase;

  @GetMapping("/api/properties/{id}")
  public PropertyDetailDataResponse find(@PathVariable long id) {
    return findPropertyUseCase.find(id);
  }

  @GetMapping("/api/properties")
  public Slice<PropertyDataResponse> find(@Valid FindPropertyRequest request) {
    return findPropertyUseCase.find(request.toCommand());
  }
}
