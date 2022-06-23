package com.appsdeveloperblog.app.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.appsdeveloperblog.app.ws"})
public class MobileAppWsApplication {
	public static void main(String[] args) {
		SpringApplication.run(MobileAppWsApplication.class, args);
	}

}