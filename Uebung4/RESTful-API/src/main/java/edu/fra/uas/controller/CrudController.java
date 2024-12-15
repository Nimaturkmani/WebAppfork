package edu.fra.uas.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.fra.uas.model.User;
import edu.fra.uas.service.UserService;

@RestController //Kennzeichnet die Klasse als REST-Controller
//Kombiniert die Funktionen von:
//@Controller: Ermöglicht die Verarbeitung von HTTP-Anfragen.
//@ResponseBody: Wandelt Rückgabewerte von Methoden direkt in HTTP-Antworten (z. B. JSON) um.
@RequestMapping("/crud") //Alle Routen beginnen mit /crud (z. B. /crud/users).
public class CrudController {
    
    private final Logger log = org.slf4j.LoggerFactory.getLogger(CrudController.class);

    @Autowired //pring injiziert automatisch die Abhängigkeit (UserService) in den Controller.
    private UserService userService;

    @GetMapping("/users")
    public List<User> list() {
        log.debug("list() is called");
        Iterable<User> userIter = userService.getAllUsers();
        List<User> users = new ArrayList<>();
        for (User user : userIter) { //Iteriert durch die Sammlung und fügt die Benutzer zu einer ArrayList<User> hinzu.
            users.add(user); // notwendig, da Iterable nicht direkt zurückgegeben werden kann.
        }
        return users; //Gibt die Liste der Benutzer als JSON zurück.
    }

    @GetMapping("/users/{id}")
    public User find(@PathVariable("id") Long userId) { //pathVariable->Extrahiert die Benutzer-ID aus der URL
        log.debug("find() is called");
        User user = userService.getUserById(userId);
        return user;
    }

    @PostMapping("/users") //Erstellt einen neuen Benutzer
    public User add(User user) {
        log.debug("add() is called");
        return userService.createUser(user);
    }

    @PutMapping("/users/{id}") //ktualisiert die Informationen eines vorhandenen Benutzers.
    public User update(User newUser, @PathVariable("id") Long userId) { //newUser->Bindet das aktualisierte Benutzerobjekt aus der HTTP-Anfrage
        log.debug("update() is called");
        User user = userService.getUserById(userId);
        user.setRole(newUser.getRole());
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setEmail(newUser.getEmail());
        user.setPassword(newUser.getPassword());
        return userService.updateUser(user);
    }

    @DeleteMapping("/users/{id}")
    public void delete(@PathVariable("id") Long userId) {
        log.debug("delete() is called");
        userService.deleteUser(userId);
    }

}
