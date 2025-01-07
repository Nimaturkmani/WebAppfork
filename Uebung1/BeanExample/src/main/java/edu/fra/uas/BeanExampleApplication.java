package edu.fra.uas;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication // marks the main class of the application
public class BeanExampleApplication {
	public static void main(String[] args) {
		SpringApplication.run(BeanExampleApplication.class, args); // initializing and lunching the app
	}

	@Bean // wenn bean einkommentiert wird wird die hello world nicht in Konsole ausgegeben
	CommandLineRunner init() { // dass Container nach dem Start der Anwendung die Bean unmittelbar ausführt
		CommandLineRunner action = new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				System.out.println("Hello World!");
			}
		};
		return action;
	}

}
