package com.realestate.service.common.advice;

import java.lang.reflect.Type;

import com.realestate.service.common.response.ApiResponseModel;
import com.realestate.service.common.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Slf4j
@RestControllerAdvice(
        basePackages = {"com.realestate.service"}
)
public class CustomResponseBodyAdvice implements ResponseBodyAdvice<Object> {

  @Override
  public boolean supports(MethodParameter returnType,
                          Class<? extends HttpMessageConverter<?>> converterType) {

    Type type = GenericTypeResolver.resolveType(this.getGenericType(returnType),
          returnType.getContainingClass());

    if (Void.class.getName().equals(type.getTypeName())) {
      return false;
    } else {
      return !converterType.isAssignableFrom(ByteArrayHttpMessageConverter.class)
                  && !converterType.isAssignableFrom(ResourceHttpMessageConverter.class);
    }
  }


  @Override
  public Object beforeBodyWrite(
          Object body,
          MethodParameter returnType,
          MediaType selectedContentType,
          Class<? extends HttpMessageConverter<?>> selectedConverterType,
          ServerHttpRequest request, ServerHttpResponse response) {
    if (body instanceof ErrorResponse) {
      ErrorResponse errorResponse = (ErrorResponse)body;
      response.setStatusCode(errorResponse.getStatus());
      return ApiResponseModel.builder()
              .success(false)
              .message(errorResponse.getMessage())
              .data(null)
              .build();
    } else {
      return ApiResponseModel.builder()
              .success(true)
              .message(null)
              .data(body)
              .build();
    }
  }

  private HttpStatus getStatus(ServerHttpResponse serverHttpResponse) {
    return HttpStatus.valueOf(
            ((ServletServerHttpResponse)serverHttpResponse).getServletResponse().getStatus()
    );
  }

  private Type getGenericType(MethodParameter returnType) {
    return HttpEntity.class
            .isAssignableFrom(returnType.getParameterType()) ? ResolvableType
            .forType(returnType.getGenericParameterType())
            .getGeneric(new int[0]).getType() : returnType.getGenericParameterType();
  }
}
