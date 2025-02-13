package com.example.employessytem.service.impl;

import com.example.employessytem.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Service
public class IJwtService {

  @Value("${application.security.jwt.secret-key}")
  private String secretKey;

  @Value("${application.security.jwt.expiration}")
  private long jwtExpiration;

  @Value("${application.security.jwt.refresh-token.expiration}")
  private long refreshTokenExpiration;

  public String generateToken(User user) {
    return buildToken(user, jwtExpiration);
  }

  public String generateRefreshToken(User user) {
    return buildToken(user, refreshTokenExpiration);
  }

  private String buildToken(User user, long expiration) {
    return Jwts.builder()
        .id(user.getId().toString())
        .claims(Map.of("name", user.getName()))
        .subject(user.getEmail())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(getSecretKey())
        .compact();
  }

  public Claims getClaims(String token) {
    return Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token).getPayload();
  }

  public String getSubject(String token) {
    return getClaims(token).getSubject();
  }

  public boolean isValidToken(String token) {
    return getClaims(token).getExpiration().after(new Date(System.currentTimeMillis()));
  }

  public boolean isValidTokenPerUser( String token, String email) {
    String tokenEmail = getSubject(token);
    return (email.equals(tokenEmail) && !isTokenExpired(token));
  }

  public boolean isTokenExpired(String token) {
    return getExpirationDateFromToken(token).before(new Date(System.currentTimeMillis()));
  }

  public Date getExpirationDateFromToken(String token) {
    return getClaims(token).getExpiration();
  }

  public Long getUserIdFromToken(String token) {
    if (token.startsWith("Bearer ")) {
      token = token.substring(7);
    }

    Claims claims = getClaims(token);
    return Long.parseLong(claims.getId());
  }

  private SecretKey getSecretKey() {
    byte[] secretBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(secretBytes);
  }

}
