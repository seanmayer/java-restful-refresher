package com.appsdeveloperblog.app.ws.shared;

import com.appsdeveloperblog.app.ws.security.SecurityConstants;
import com.appsdeveloperblog.app.ws.security.TokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class Utils {

  private final Random RANDOM = new SecureRandom();
  private final String ALPHABET =
    "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

  public String generatedUserId(int length) {
    return generateRandomString(length);
  }

  public String generateAddressId(int length) {
    return generateRandomString(length);
  }

  private String generateRandomString(int length) {
    StringBuilder returnValue = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
    }
    return new String(returnValue);
  }

  public static boolean hasTokenExpired(String token) {
    boolean returnValue = false;

    try {
      Claims claims = Jwts
        .parser()
        .setSigningKey(TokenUtil.getSecretKey())
        .parseClaimsJws(token)
        .getBody();

      Date tokenExpirationDate = claims.getExpiration();
      Date todayDate = new Date();

      return tokenExpirationDate.before(todayDate);
    } catch (JwtException ex) {
      returnValue = true;
    }

    return returnValue;
  }

  public String generateEmailVerificationToken(String userId) {
    String token = Jwts
      .builder()
      .setId(userId)
      .setExpiration(
        new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME)
      )
      .signWith(TokenUtil.getSecretKey())
      .compact();

    return token;
  }

  public String generatePasswordResetToken(String userId) {
    String token = Jwts
      .builder()
      .setId(userId)
      .setExpiration(
        new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME)
      )
      .signWith(TokenUtil.getSecretKey())
      .compact();

    return token;
  }
}
