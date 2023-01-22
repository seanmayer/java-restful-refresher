package com.appsdeveloperblog.app.ws.security;

import com.appsdeveloperblog.app.ws.io.repositories.UserRepository;
import com.appsdeveloperblog.app.ws.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableGlobalMethodSecurity(securedEnabled = true /*,prePostEnabled = true*/ )
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

  private final UserService userDetailsService;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final UserRepository userRepository;

  public WebSecurity(
    UserService userDetailsService,
    BCryptPasswordEncoder bCryptPasswordEncoder,
    UserRepository userRepository
  ) {
    this.userDetailsService = userDetailsService;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.userRepository = userRepository;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .cors()
      .and()
      .csrf()
      .disable()
      .authorizeRequests()
      .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL)
      .permitAll()
      .antMatchers(HttpMethod.GET, SecurityConstants.VERIFICATION_EMAIL_URL)
      .permitAll()
      .antMatchers(
        HttpMethod.POST,
        SecurityConstants.PASSWORD_RESET_REQUEST_URL
      )
      .permitAll()
      .antMatchers(HttpMethod.POST, SecurityConstants.PASSWORD_RESET_URL)
      .permitAll()
      .antMatchers(SecurityConstants.H2_CONSOLE)
      .permitAll()
      .antMatchers(
        "/v2/api-docs",
        "/swagger-resources/configuration/ui",
        "/swagger-resources",
        "/swagger-resources/configuration/security",
        "/swagger-ui.html",
        "/webjars/**"
      )
      .permitAll()
      //.antMatchers(HttpMethod.DELETE, "/users/**")
      //.hasAnyRole("ADMIN","SUPER_ADMIN")
      .anyRequest()
      .authenticated()
      .and()
      .addFilter(getAuthenticationFilter())
      .addFilter(
        new AuthorizationFilter(authenticationManager(), userRepository)
      )
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    // H2 console configuration only for development not for production
    http.headers().frameOptions().disable();
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
      .userDetailsService(userDetailsService)
      .passwordEncoder(bCryptPasswordEncoder);
  }

  public AuthenticationFilter getAuthenticationFilter() throws Exception {
    final AuthenticationFilter filter = new AuthenticationFilter(
      authenticationManager()
    );
    filter.setFilterProcessesUrl("/users/login");
    return filter;
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    final CorsConfiguration config = new CorsConfiguration();

    config.setAllowCredentials(false);
    config.addAllowedOrigin("*");
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");

    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

    source.registerCorsConfiguration("/**", config);

    return source;
  }
}
