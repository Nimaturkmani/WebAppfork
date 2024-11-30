package edu.fra.uas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import edu.fra.uas.service.MessageService;

@SpringBootApplication
public class BeanExampleApplication {

	@Autowired // -> wird ein Bean obeject zu verfügung gestellt und beinhaltet auch seine Instanziierung
	private MessageService messageService; // objekt von messageService erstellt

	public static void main(String[] args) {
		SpringApplication.run(BeanExampleApplication.class, args);
	}

	@Bean
	CommandLineRunner init() {// hier wird message service objekte ausgerufen und die werte haben wir bekommen
		CommandLineRunner action = new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				messageService.setMessage("Hello World!"); // durch setter wird gesetzt
				System.out.println(messageService.getMessage()); // durch getter geholt
				messageService.setMessage("--> HHHOHHH <--"); // durch setter wieder gesetzt
				System.out.println(messageService.getMessage()); // durch getter geholt und ausgegeben
			}
		};
		return action;
	}

}
