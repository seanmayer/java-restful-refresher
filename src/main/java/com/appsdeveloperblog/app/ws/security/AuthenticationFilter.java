package com.appsdeveloperblog.app.ws.security;

import com.appsdeveloperblog.app.ws.SpringApplicationContext;
import com.appsdeveloperblog.app.ws.service.UserService;
import com.appsdeveloperblog.app.ws.shared.dto.UserDto;
import com.appsdeveloperblog.app.ws.ui.model.request.UserLoginRequestModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;

  public AuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  public Authentication attemptAuthentication(
    HttpServletRequest req,
    HttpServletResponse res
  )
    throws AuthenticationException {
    try {
      UserLoginRequestModel creds = new ObjectMapper()
      .readValue(req.getInputStream(), UserLoginRequestModel.class);

      return authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
          creds.getEmail(),
          creds.getPassword(),
          new ArrayList<>()
        )
      );
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(
    HttpServletRequest req,
    HttpServletResponse res,
    FilterChain chain,
    Authentication auth
  )
    throws IOException, ServletException {
    String userName = ((UserPrinciple) auth.getPrincipal()).getUsername();
    String token = TokenUtil.generateToken(userName);

    UserService userService = (UserService) SpringApplicationContext.getBean(
      "userServiceImpl"
    );
    UserDto userDto = userService.getUser(userName);

    res.addHeader(
      SecurityConstants.HEADER_STRING,
      SecurityConstants.TOKEN_PREFIX + token
    );

    res.addHeader("userId", userDto.getUserId());
  }
}
