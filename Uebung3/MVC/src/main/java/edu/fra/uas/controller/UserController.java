package edu.fra.uas.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.fra.uas.model.User;
import edu.fra.uas.service.UserService;
//HTTP-Anfragen empfängt und daraufhin entsprechende HTML-Seiten zurückgibt
@Controller
public class UserController {

    private final Logger log = org.slf4j.LoggerFactory.getLogger(UserController.class); //Initialisiert einen Logger für Debugging-Ausgaben

    @Autowired
    private UserService userService;
    //Spring injiziert automatisch eine Instanz des UserService, der für Benutzeroperationen verantwortlich ist
    //(z. B. Abrufen, Erstellen, Löschen von Benutzern).

    // http://127.0.0.1/
    @RequestMapping //ohne spezifischen Pfad: Diese Methode wird für die Root-URL (http://127.0.0.1/) aufgerufen
	
    public String get() { //Die Methode gibt den Namen der HTML-View-Datei zurück, die geladen werden soll
        log.debug("get() is called");
		return "index.html";
	}

    // http://127.0.0.1/list
    @RequestMapping(value = {"/list"}, method = RequestMethod.GET) //Verknüpft diese Methode mit der URL http://127.0.0.1/list und der HTTP-Methode GET
    
    public String list(Model model) {
        log.debug("list() is called");
        Iterable<User> userIter = userService.getAllUsers(); //Ruft alle Benutzer aus dem UserService ab
        List<User> users = new ArrayList<>(); //Initialisiert eine neue Liste, um Benutzer zu speichern.
        for (User user : userIter) { // Iteriert durch die Benutzer und fügt sie der Liste hinzu
            users.add(user); 
        }
        model.addAttribute("users", users); //Übergibt die Benutzerliste an die View, sodass sie in list.html angezeigt werden kann.
        return "list.html"; //Gibt die View-Datei zurück, die die Benutzerliste rendern soll.
    }

    // http://127.0.0.1/find?id=1
    @RequestMapping(value = {"/find"}, method = RequestMethod.GET)
    
    public String find(@RequestParam("id") Long userId, Model model) { //Erwartet einen Query-Parameter id in der URL, speichert dessen Wert in der Variablen userId
        log.debug("find() is called"); 
        User user = userService.getUserById(userId);  //Holt den Benutzer mit der entsprechenden ID vom Service
        model.addAttribute("user", user); //Fügt den gefundenen Benutzer dem Model hinzu, sodass er in find.html angezeigt wird
        return "find.html"; //Gibt die HTML-View zurück
    }

    // http://127.0.0.1/add?firstName=Celine&lastName=Clever&email=celine.clever%40example.com&password=123456
    @RequestMapping(value = {"/add"}, method = RequestMethod.GET)
    
    public String add(@RequestParam("firstName") String firstName, 
                      @RequestParam("lastName") String lastName, 
                      @RequestParam("email") String email, 
                      @RequestParam("password") String password, 
                      Model model) throws MissingServletRequestParameterException {   //Das Model ist eine Schnittstelle (org.springframework.ui.Model), 
                            	                                                     //die als Container für Daten dient, die in der View verwendet werden sollen.
        log.debug("add() is called");
        User user = new User();
        user.setRole("USER");
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        userService.createUser(user);
        model.addAttribute("user", user);
        return "add.html";
    }

    // http://127.0.0.1/update
    @RequestMapping(value = {"/update"}, method = RequestMethod.GET)
    
    public String update() { //Zeigt die Update Seite an, auf der der Benutzer Daten eingeben kann, um ein Benutzerobjekt zu aktualisieren
        log.debug("update() is called");
        return "update.html";
    }

    // http://127.0.0.1/updated?id=2&firstName=Alice&lastName=Abraham&email=alice%40example.com&password=123A456
    @RequestMapping(value = {"/updated"}, method = { RequestMethod.GET, RequestMethod.POST })
   
    public String update(@RequestParam("id") Long userId, //Verarbeitet die aktualisierten Daten, speichert sie in der Datenbank und gibt eine Bestätigungsseite zurück
                         @RequestParam("firstName") String firstName, 
                         @RequestParam("lastName") String lastName, 
                         @RequestParam("email") String email, 
                         @RequestParam("password") String password, 
                         Model model) {
        log.debug("updated() is called");
        User user = userService.getUserById(userId);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        userService.updateUser(user);
        model.addAttribute("user", user);
        return "updated.html";
    }

    // http://127.0.0.1/delete/3
    @RequestMapping(value = {"/delete/{id}"}, method = RequestMethod.GET)
    
    public String delete(@PathVariable("id") Long id, Model model) { //@PathVariable("id") Long id: Extrahiert die ID aus der URL (z. B. /delete/3)
        log.debug("delete() is called");
        User user = userService.getUserById(id);
        userService.deleteUser(id);
        model.addAttribute("user", user);
        return "deleted.html";
    }

}
