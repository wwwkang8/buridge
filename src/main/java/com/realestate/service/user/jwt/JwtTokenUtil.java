package com.realestate.service.user.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

  private static final long JWT_TOKEN_VALIDITY = 1000L * 60 * 60 * 24; // 24시간 유효

  @Value("${jwt.secret")
  private String secretCode;

  /** User의 email을 JWT에서 가져온다 */
  public String getUseremailFromToken(String token) {
    return Jwts.parser().setSigningKey(secretCode).parseClaimsJws(token).getBody().getSubject();
  }

  /** JWT 토큰에서 토큰만료일자를 가져온다 */
  public Date getExpirationDateFromToken(String token) {
    return Jwts.parser().setSigningKey(secretCode).parseClaimsJws(token).getBody().getExpiration();
  }

  /** 토큰이 만료되었는지 확인 */
  public Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  /** 유저 이메일에 대해서 토큰 발행요청 */
  public String generateToken(UserDetails userDetails) {

    // claim은 Payload에 담을 정보를 의미한다. 여러개의 정보를 담을수 있고, key/value로 만들기 위해 map 사용.
    // Payload 에 담는 정보의 한 ‘조각’ 을 클레임이라고 부른다.
    Map<String, Object> claims = new HashMap<>(); // 현재는 따로 추가할 정보가 없어서 빈 클레임
    return doGenerateToken(claims, userDetails.getUsername());
  }

  /** 토큰 발행 시작 */
  public String doGenerateToken(Map<String, Object> claims, String email) {
    Date now = new Date();

    return Jwts.builder().setClaims(claims).setSubject(email).setIssuedAt(new Date(now.getTime()))
               .setExpiration(new Date(now.getTime() + JWT_TOKEN_VALIDITY))
               .signWith(SignatureAlgorithm.HS512, secretCode).compact();
  }

  /** 토큰 인증하기 */
  public Boolean validateToken(String token, UserDetails userDetails) {
    final String emailFromToken = getUseremailFromToken(token);

    return (emailFromToken.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

}
