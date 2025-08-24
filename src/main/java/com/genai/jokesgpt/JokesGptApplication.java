package com.genai.jokesgpt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the JokesGPT Spring Boot application.
 * This class bootstraps the application and starts the embedded server.
 */
@SpringBootApplication // Enables auto-configuration and component scanning
public class JokesGptApplication {

	/**
	 * Main method that launches the Spring Boot application.
	 *
	 * @param args Command-line arguments passed during startup.
	 */
	public static void main(String[] args) {
		SpringApplication.run(JokesGptApplication.class, args);
	}

}
