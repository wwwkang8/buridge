package com.realestate.service.user.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : currentUser")
public @interface CurrentUser {


}
