package com.appsdeveloperblog.app.ws.security;

import java.util.ArrayList;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

public class AuthorizationFilter extends BasicAuthenticationFilter{
 
 public AuthorizationFilter(AuthenticationManager authManager) {
  super(authManager);
 }

 @Override
 protected void doFilterInternal(
   HttpServletRequest req,
   HttpServletResponse res,
   FilterChain chain) throws java.io.IOException, javax.servlet.ServletException {
  
  String header = req.getHeader(SecurityConstants.HEADER_STRING);

  if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
   chain.doFilter(req, res);
   return;
  }

  UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
  SecurityContextHolder.getContext().setAuthentication(authentication);
  chain.doFilter(req, res);
 }

 private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
  String token = request.getHeader(SecurityConstants.HEADER_STRING);
  if (token != null) {
   token = token.replace(SecurityConstants.TOKEN_PREFIX, "");
 
   SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
   String base64Key = Encoders.BASE64.encode(key.getEncoded());
   String user = Jwts.parser()
   .setSigningKey(Keys.hmacShaKeyFor(base64Key.getBytes()))
   .parseClaimsJws(token)
   .getBody()
   .getSubject();

   if (user != null) {
    return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
   }
  }
  return null;
 }
 
}
