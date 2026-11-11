package org.gecko.playground.greetings.spring.application;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot entry point. It scans all greetings packages, so Spring picks up
 * {@code GreetingsServiceImpl} and {@code GreetingResource} as components, just
 * as DS does in the OSGi application.
 */
@SpringBootApplication(scanBasePackages = "org.gecko.playground.greetings")
public class RestServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestServiceApplication.class, args);
	}

}
