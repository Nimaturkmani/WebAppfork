package edu.fra.uas.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.fra.uas.session.Session;

@Controller
public class ApplicationController {

	private static final Logger log = LoggerFactory.getLogger(ApplicationController.class);
	
	@Autowired //Spring injiziert automatisch die Bean ApplicationContext
	ApplicationContext applicationContext; 
	// ApplicationContext, eine zentrale Klasse in Spring, die den Zugriff auf alle registrierten Beans ermöglicht.
	//Über den ApplicationContext kann man Beans zur Laufzeit abrufen, anstatt sie manuell zu instanziieren.
	
	public ApplicationController() {}
	//Ein leerer Standardkonstruktor. Da keine zusätzlichen Abhängigkeiten oder Logik im Konstruktor benötigt werden, ist er leer.

	
	@RequestMapping(value = "/path1") //Definiert die URL, unter der diese Methode aufgerufen wird(/path1).
	public String showPage1() {
		Session session1 = (Session) applicationContext.getBean(Session.class); //wird eine Bean der Klasse Session abgerufen
		Session session2 = (Session) applicationContext.getBean(Session.class);
		log.info(session1.getSessionName());
		log.info(session2.getSessionName());
		return "page";
	}
	
	@RequestMapping(value = "/path2")
	public String showPage2() {
		Session session1 = (Session) applicationContext.getBean(Session.class);
		Session session2 = (Session) applicationContext.getBean(Session.class);
		log.info(session1.getSessionName());
		log.info(session2.getSessionName());
		return "page"; //Gibt den Namen der View (z. B. page.html oder page.jsp) zurück, die dem Benutzer angezeigt werden soll
	}
	
}
