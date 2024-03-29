package com.appsdeveloperblog.app.ws.security;

import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.security.Key;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import com.appsdeveloperblog.app.ws.io.repositories.UserRepository;

public class AuthorizationFilter extends BasicAuthenticationFilter {

  private final UserRepository UserRepository;

  public AuthorizationFilter(AuthenticationManager authManager, UserRepository UserRepository) {
    super(authManager);
    this.UserRepository = UserRepository;
  }

  @Override
  protected void doFilterInternal(
    HttpServletRequest req,
    HttpServletResponse res,
    FilterChain chain
  )
    throws IOException, ServletException {
    String header = req.getHeader(SecurityConstants.HEADER_STRING);

    if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
      chain.doFilter(req, res);
      return;
    }

    UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    chain.doFilter(req, res);
  }

  private UsernamePasswordAuthenticationToken getAuthentication(
    HttpServletRequest request
  ) {
    String token = request.getHeader(SecurityConstants.HEADER_STRING);

    if (token != null) {
      token = token.replace(SecurityConstants.TOKEN_PREFIX, "");
      Key key = TokenUtil.getSecretKey();

      TokenUtil.verifyToken(token);

      String user = Jwts
        .parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();

      if (user != null) {
        UserEntity userEntity = UserRepository.findByEmail(user);
        if(userEntity == null) return null;
        UserPrinciple userPrinciple = new UserPrinciple(userEntity);
        return new UsernamePasswordAuthenticationToken(
          userPrinciple,
          null,
          userPrinciple.getAuthorities()
        );
      }
    }
    return null;
  }
}
