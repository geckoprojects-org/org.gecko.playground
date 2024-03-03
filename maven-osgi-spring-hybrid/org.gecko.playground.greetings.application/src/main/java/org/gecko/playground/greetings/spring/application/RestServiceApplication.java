package org.gecko.playground.greetings.spring.application;


import org.gecko.playground.greetings.api.GreetingsService;
import org.gecko.playground.greetings.impl.GreetingsServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RestServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestServiceApplication.class, args);
	}
	
	@Bean
    GreetingsService iotClient() {
        return new GreetingsServiceImpl();
    }

}
