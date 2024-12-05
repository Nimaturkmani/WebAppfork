package edu.fra.uas.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Service // for business logic
@Component // managed component
@Repository // for persistence
public class SmallService {    //simple example of using lifecycle annotations

	private static final Logger log = LoggerFactory.getLogger(SmallService.class);
	
	private String state = "preInitialization";

	@PostConstruct
    public void postConstruct() {   //When the SmallService bean is created and fully initialized by Spring, the postConstruct() method is executed.
		log.info("postConstruct() -->  " + state);
		state = "PostConstruct";
		log.info("postConstruct() -->  " + state);
	}
	
	public void doSomething() {  //This method will log the current state
		log.info("doSomething() --> " + state);
	}
	
	@PreDestroy
    public void preDestroy() { //Before the bean is destroyed, the preDestroy() method will be called.
		state = "PreDestroy";
		log.info("preDestroy() --> " + state);
	}
	
}
