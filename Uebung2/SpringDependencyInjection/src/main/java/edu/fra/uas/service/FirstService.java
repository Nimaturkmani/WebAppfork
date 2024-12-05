package edu.fra.uas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FirstService {

	// Constructor Injection: im ersten Schritt auskommentieren
	@Autowired //Field Injection (Not Recommended): directly injects the dependency into the field, bypassing the constructor and setter methods!!
	private SecondService secondService;
	
	public FirstService() { //directly creates a new SecondService instance 
		secondService = new SecondService();
	}
	
	// Constructor Injection -->most recommended way of dependency injection in Spring ->dependencies can't be changed after instantiation
	@Autowired //uses @Autowired to inject this dependency via the constructor.
	public FirstService(SecondService secondService) { //takes a SecondService object as a parameter
		this.secondService = secondService;
	} // Spring will automatically inject the SecondService bean when creating the FirstService bean.

	//Setter Injection
	//allows Spring to inject the SecondService dependency through a setter method after the object has been created
	@Autowired 
	public void setSecondService(SecondService secondService) { //allowing to inject the SecondService after the FirstService bean has been instantiated.
		this.secondService = secondService;
	}
	
	public void doSomething() {
		secondService.doSomething();
	}
	
}
