package com.appsdeveloperblog.app.ws.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;

public class TokenUtil {

  private static final SecretKey secret = Keys.secretKeyFor(
    SignatureAlgorithm.HS512
  );
  private static final byte[] secretBytes = secret.getEncoded();
  private static final String base64SecretBytes = Base64
    .getEncoder()
    .encodeToString(secretBytes);

  public static SecretKey getSecretKey() {
    return secret;
  }

  public static String generateToken(String username) {
    String id = UUID.randomUUID().toString().replace("-", "");
    Date now = new Date();
    Date exp = new Date(System.currentTimeMillis() + (1000 * 120)); // 120 seconds

    String token = Jwts
      .builder()
      .setId(id)
      .setSubject(username)
      .setIssuedAt(now)
      .setNotBefore(now)
      .setExpiration(exp)
      .signWith(SignatureAlgorithm.HS512, base64SecretBytes)
      .compact();

    return token;
  }

  public static void verifyToken(String token) {
    Claims claims = Jwts
      .parser()
      .setSigningKey(base64SecretBytes)
      .parseClaimsJws(token)
      .getBody();
    System.out.println("----------------------------");
    System.out.println("ID: " + claims.getId());
    System.out.println("Subject: " + claims.getSubject());
    System.out.println("Issuer: " + claims.getIssuer());
    System.out.println("Expiration: " + claims.getExpiration());
  }
}
