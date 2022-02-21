package com.realestate.service.config;

import com.realestate.service.user.jwt.JwtAuthenticationEntryPoint;
import com.realestate.service.user.jwt.JwtRequestFilter;
import com.realestate.service.user.jwt.JwtUserDetailService;
import com.realestate.service.user.service.PasswordEncoderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

  private final JwtRequestFilter jwtRequestFilter;

  private final JwtUserDetailService jwtUserDetailService;

  private final PasswordEncoderService passwordEncoderService;


  /**
   * AuthenticationManager를 설정하여 user의 인증정보를 암호화
   * JwtUserDetailService에 이메일과 비밀번호가 존재함.
   * */
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

    auth.userDetailsService(jwtUserDetailService)
        .passwordEncoder(passwordEncoderService.getPasswordEncoder());
  }


  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }


  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.cors().disable() //CORS 정책 해제
                .csrf().disable()
                .formLogin().disable() // Spring 시큐리시 사용하면 디폴트로 나오는 로그인 폼 사용 해제
                .authorizeRequests().antMatchers("/api/users/authenticate", "/api/users/signup")
                .permitAll()
                .anyRequest().authenticated().and() //위 2개의 URI 제외하고 모두 인증 필요.
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .headers().frameOptions().disable();

    /**
     * JwtRequestFilter를 UsernamePasswordAuthenticationFilter.class보다 앞서서 실행될 수 있도록 하는 설정.
     * JwtRequestFilter 용도 : 1) header에서 토큰 추출 2) 유효한 토큰인지 확인
     *
     * [UsernamePasswordAuthenticationFilter 용도]
     * 스프링 시큐리티에서 제공하는 로그인폼(username, password)를 입력했을 때 검증하는 필터.
     * --> 우리는 JWT 토큰으로 검증하기 떄문에 위 필터가 필요가 없다.
     * */
    httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
  }
}
