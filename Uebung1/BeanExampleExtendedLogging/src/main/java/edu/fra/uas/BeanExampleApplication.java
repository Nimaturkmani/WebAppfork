package edu.fra.uas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import edu.fra.uas.controller.BeanController;

@SpringBootApplication
public class BeanExampleApplication {

	private static final Logger log = LoggerFactory.getLogger(BeanExampleApplication.class);

	@Autowired
	private BeanController beanController;

	public static void main(String[] args) {
		SpringApplication.run(BeanExampleApplication.class, args);
	}

	@Bean
	CommandLineRunner init() {
		CommandLineRunner action = new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				// das hier hat nur ein Ausgabe weil die Wert wird 2 Mal überschrieben und nur beim letzen mal ausgegeben!
				log.debug(beanController.putMessage("Hello World"));
				log.debug(beanController.putMessage("--> OOOHOOO <--"));
				System.out.println(beanController.putMessage("hiiiii"));
				//my test method nothing to do with aufgabe!
				String txt = "Hello world and hello txt";
				beanController.readMessage(txt);
			}
		};
		return action;
	}

}
