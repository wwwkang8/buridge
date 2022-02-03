package com.realestate.service.common.advice;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.realestate.service.common.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

  /**
   * BindException handler.
   */
  @ExceptionHandler(BindException.class)
  protected ErrorResponse handleBindException(BindException e) {
    log.error("BindException error", e);
    BindingResult bindingResult = e.getBindingResult();
    return ErrorResponse.builder()
              .message(bindingResult.getAllErrors().get(0).getDefaultMessage())
              .status(HttpStatus.BAD_REQUEST)
              .build();
  }

  /**
   * HttpMessageConversionException handler.
   */
  @ExceptionHandler(HttpMessageConversionException.class)
  public ErrorResponse httpMessageConversionExceptionHandler(
          HttpMessageConversionException e) {

    log.error("HttpMessageConversionException error", e);

    Throwable cause = e.getCause();

    log.error("http message convert error stack tracing", cause);

    if (cause instanceof InvalidDefinitionException) {
      if (cause.getCause() != null && cause.getCause().getMessage() != null) {
        return ErrorResponse.builder()
                .message(cause.getCause().getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build();
      }
    }
    return ErrorResponse.builder()
            .message(e.getMessage())
            .status(HttpStatus.BAD_REQUEST)
            .build();
  }

  /**
   * MethodArgumentNotValidException handler.
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ErrorResponse methodArgumentNotValidExceptionHandler(
          MethodArgumentNotValidException e) {
    log.error("MethodArgumentNotValidException error", e);
    return ErrorResponse.builder()
            .message("잘못된 요청입니다.")
            .status(HttpStatus.BAD_REQUEST)
            .build();
  }

  /**
   * Exception handler.
   */
  @ExceptionHandler(Exception.class)
  protected ErrorResponse handleException(Exception e) {
    log.error("handleException error", e);
    return ErrorResponse.builder()
              .message(e.toString())
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .build();
  }
}
