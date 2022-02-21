package com.realestate.service.user.jwt;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

  private final JwtTokenUtil jwtTokenUtil;

  private final JwtUserDetailService jwtUserDetailService;


  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {


    final String requestTokenHeader = request.getHeader("Authorization");

    String email = null;
    String jwtToken = null;



    /**
     * request 헤더에서 토큰을 가져온다.
     * 그리고 해당 토큰으로 사용자 이메일을 조회한다.
     * */
    if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
      log.info("전체 헤더" + requestTokenHeader);
      jwtToken = requestTokenHeader.substring(7);
      log.info("jwt token : " + jwtToken);

      jwtToken = requestTokenHeader.substring(7);
      try {
        email = jwtTokenUtil.getUseremailFromToken(jwtToken);
      } catch (IllegalArgumentException e) {
        System.out.println("Unable to get JWT Token");
      } catch (ExpiredJwtException e) {
        System.out.println("JWT Token has expired");
      }
    } else {
      log.info("토큰 추출시 오류 발생!! 1) 헤더에 Authorization 확인 2) 토큰헤더가 Bearer로 시작하는지 확인");
    }

    /**
     * 토큰을 가지고 오면 유효한지 검증
     * */
    if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {


      UserDetails userDetails = jwtUserDetailService.loadUserByUsername(email);

      // if token is valid configure Spring Security to manually set
      // authentication
      if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

        // 참고링크 : https://cjw-awdsd.tistory.com/45
        /**
         * UsernamePasswordAuthenticationToken 생성시 마지막 파라메터 authorities 확인하기
         * */
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
            = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken
            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        /**
         * 인증정보를 context에 세팅한 후, 해당 사용자가 인증되었다는 것을 확인.
         * 그래서 context 정보는 Spring Security 설정정보에 성공적으로 전달됨.
         * */
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
      }
    }
    filterChain.doFilter(request, response);



  }
}
