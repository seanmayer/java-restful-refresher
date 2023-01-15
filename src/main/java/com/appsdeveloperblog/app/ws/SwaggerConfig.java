package com.appsdeveloperblog.app.ws;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  final Contact contact = new Contact(
    "Apps Developer Blog",
    "https://scalablehuman.com/",
    ""
  );

  final List<VendorExtension> vendorExtension = new ArrayList<>();

  ApiInfo apiInfo = new ApiInfo(
    "REST API Documentation",
    "REST API Documentation",
    "1.0",
    "Terms of service",
    contact,
    "License of API",
    "https://scalablehuman.com/",
    vendorExtension
  );

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
      .protocols(new HashSet<>(Arrays.asList("HTTP", "HTTPS")))
      .apiInfo(apiInfo)
      .select()
      .apis(RequestHandlerSelectors.basePackage("com.appsdeveloperblog.app.ws"))
      .paths(PathSelectors.any())
      .build();
  }
}
