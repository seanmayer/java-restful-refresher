package com.appsdeveloperblog.app.ws.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class AppProperties {
 @Autowired
 private Environment env;

 //not in use but demonstrates how to get properties from application.properties file
 //the actual token secret is generated programmtically please read: https://github.com/jwtk/jjwt#creating-safe-keys
 public String getTokenSecret() {
  return env.getProperty("tokenSecret");
 }
}
