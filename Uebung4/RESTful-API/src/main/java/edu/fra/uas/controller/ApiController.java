package edu.fra.uas.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn; // statische Methoden um HATEOAS-Links dynamisch zu generieren.

import java.net.URI; // URI für eindeutige Ressourcenschlüssel
import java.util.List; //List für die Verarbeitung von Benutzerlisten

import org.slf4j.Logger; //Für Logging von Debug-Informationen
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.fra.uas.model.User;
import edu.fra.uas.model.UserDTO;
import edu.fra.uas.service.UserService;
import edu.fra.uas.util.Partition;




@RestController //Markiert die Klasse als REST-Controller, die HTTP-Anfragen entgegennimmt
                //und JSON- oder XML-Responses zurückgibt.

@RequestMapping("/api") //Präfix für alle URLs dieser API, z. B. /api/users.
public class ApiController {
    
    private final Logger log = org.slf4j.LoggerFactory.getLogger(ApiController.class);

    @Autowired  //Der UserService wird mit @Autowired injiziert.
    private UserService userService; //Er übernimmt die eigentliche Geschäftslogik und die Interaktion mit der Datenbank

    private static final int MAX_USERS = 2; //Gibt an, wie viele Benutzer pro Seite zurückgegeben werden -> Max Zwei

    @GetMapping(value = "/users", // Diese Methode wird aufgerufen, wenn der Endpunkt /users mit einer HTTP-GET-Anfrage angesprochen wird.
                produces = MediaType.APPLICATION_JSON_VALUE) //produces: Die Antwort wird als JSON zurückgegeben
    @ResponseBody //Signalisiert, dass die Rückgabe direkt in die HTTP-Antwort geschrieben wird
   
    // Methode-> die Benutzer über eine REST-API auflistet
    public ResponseEntity<CollectionModel<UserDTO>> list(@RequestParam(required = false) Integer page) { 
        log.debug("list() is called");
        
        //Ein UserDTO ist eine abgespeckte Darstellung eines Benutzerobjekts, die speziell für den Datentransfer 
        //in der API verwendet wird.
        List<UserDTO> users = userService.getAllUsersDTO();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build(); //ResponseEntity: rlaubt es, HTTP-Statuscodes zusammen mit der Antwort zu definieren.
        }  //Wenn keine Benutzer existieren, wird eine HTTP-Antwort mit dem Statuscode 204 No Content zurückgegeben.
        
        else if (users.size() > MAX_USERS && page == null) { //Mehr Benutzer als MAX_USERS, aber keine Seite angegeben
            int lastPage = (users.size() / MAX_USERS) + 1; //Berechnung der letzten Seite
            //IanaLinkRelations.FIRST/NEXT/LAST: Standardisierte Begriffe für Navigationslinks in REST-APIs
            Link first = linkTo(methodOn(ApiController.class).list(1)).withRel(IanaLinkRelations.FIRST);
            Link next = linkTo(methodOn(ApiController.class).list(2)).withRel(IanaLinkRelations.NEXT);
            Link last = linkTo(methodOn(ApiController.class).list(lastPage)).withRel(IanaLinkRelations.LAST);
            CollectionModel<UserDTO> result = CollectionModel.of(users.subList(0, MAX_USERS)).add(first, next, last);
            for (UserDTO user : result) {
                ////Jeder Benutzer erhält einen individuellen Self-Link
                Link selfLink = linkTo(ApiController.class).slash("/users/" + user.getId()).withSelfRel(); 
                user.add(selfLink);
            }
            return new ResponseEntity<>(result, HttpStatus.PARTIAL_CONTENT); //Rückgabe mit Statuscode
        } 
        //Eine bestimmte Seite wird angefordert
        else if (page != null) { //Wenn eine Seitenzahl angegeben ist
            Partition<UserDTO> partition = Partition.ofSize(users, MAX_USERS); //Partitionieren der Benutzerliste
            CollectionModel<UserDTO> result = null;
            Link link = linkTo(methodOn(ApiController.class).list(page)).withSelfRel();
            try{ //Fehlerbehandlung -> ungültige Seitenzahl
                result = CollectionModel.of(partition.get(page - 1)).add(link);
                for (UserDTO user : result) {
                    Link selfLink = linkTo(ApiController.class).slash("/users/" + user.getId()).withSelfRel();
                    user.add(selfLink);
                }
            } catch (IndexOutOfBoundsException e) {
                return ResponseEntity.notFound().build();
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } 
        
        else { //standardfall -> Wenn keine Seitennummer angegeben ist, aber die Anzahl der Benutzer nicht größer als MAX_USERS ist
                // wird die gesamte Benutzerliste mit Links zurückgegeben
            Link link = linkTo(methodOn(ApiController.class).list(null)).withSelfRel();
            CollectionModel<UserDTO> result = CollectionModel.of(users).add(link); //CollectionModel: Enthält: Die Benutzerlist und Hypermedia-Links für Navigation und Self-Referenzen
            for (UserDTO user : result) { ////UserDTO: Stellt die Benutzerdaten dar
                Link selfLink = linkTo(ApiController.class).slash("/users/" + user.getId()).withSelfRel();
                user.add(selfLink);
            }
            return new ResponseEntity<>(CollectionModel.of(users), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/users/{id}",  //einen einzelnen Benutzer anhand seiner ID über eine REST-API abruft
                                        //{id} ist ein Pfadparameter, der dynamisch durch eine Benutzer-ID ersetzt wird (z. B. /users/1
                produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody

    public ResponseEntity<?> find(@PathVariable("id") Long userId) { //gibt eine HTTP-Antwort zurück, Inhalt (Benutzer) als auch den Statuscode 
            	                    //Extrahiert den Wert des Pfadparameters {id} und weist ihn der Variablen userId zu.
        log.debug("find() is called");
        User user = userService.getUserById(userId);
        if (user == null) {            
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<User>(user, HttpStatus.OK); // gibt den Objekt und den Stattus als HTTP zurück
    }



    @PostMapping(value = "/users",  //neue Ressourcen (z. B. Benutzer) zu erstellen
                 consumes = MediaType.APPLICATION_JSON_VALUE, //Die Methode erwartet, dass die Anfrage einen JSON-Inhalt enthält
                 produces = MediaType.APPLICATION_JSON_VALUE) //Die Antwort wird im JSON-Format zurückgegeben.
    @ResponseBody //Rückgabe direkt als HTTP-Antwort verwendet wird, ohne eine View zu rendern

    public ResponseEntity<?> add(@RequestBody User user) { //? bedeutet, dass der Rückgabewert flexibel sein kann
                                    //@RequestBody User user: Bindet den JSON-Inhalt der Anfrage an das User-Objekt
                                    //Spring konvertiert das JSON automatisch in ein Java-Objekt mithilfe von Jackson.

        log.debug("add() is called");
        String detail = null;
        if (user == null) { // Datenvalidierung -> Die Felder nicht leer sind
            detail = "User must not be null";            
        } else if (user.getRole() == null) {
            detail = "Role must not be null";
        } else if (user.getRole().isEmpty()) {
            detail = "Role must not be empty";
        } else if (user.getFirstName() == null) {
            detail = "FirstName must not be null";
        } else if (user.getFirstName().isEmpty()) {
            detail = "FirstName must not be empty";
        } else if (user.getLastName() == null) {
            detail = "LastName must not be null";
        } else if (user.getLastName().isEmpty()) {
            detail = "LastName must not be empty";
        } else if (user.getEmail() == null) {
            detail = "Email must not be null";
        } else if (user.getEmail().isEmpty()) {
            detail = "Email must not be empty";
        } else if (user.getPassword() == null) {
            detail = "Password must not be null";
        } else if (user.getPassword().isEmpty()) {
            detail = "Password must not be empty";
        }
        //Fehlerbehandlung bei ungültigen Daten
        if (detail != null) {
            ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, detail);  //Ein Objekt, das Details zum Fehler enthält
            pd.setInstance(URI.create("/users")); //Setzt den Endpunkt als Instanz für das Problem
            pd.setTitle("JSON Object Error"); 
            return ResponseEntity.unprocessableEntity().body(pd);
        }

        user = userService.createUser(user);
        HttpHeaders headers = new HttpHeaders(); //Erstellt Header für die Antwort
        headers.setLocation(URI.create("/restful/users/" + user.getId())); //Fügt einen Location-Header hinzu, der auf die neue Ressource verweist
        return new ResponseEntity<User>(user, headers, HttpStatus.CREATED);
        //ResponseEntity Enthält:
    //Das erstellte User-Objekt (als JSON).
    //Den Location-Header.
    //Den HTTP-Status 201 Created, der signalisiert, dass eine neue Ressource erfolgreich erstellt wurde.
    }

    @PutMapping(value = "/users/{id}" //einen bestehenden Benutzer über eine REST-API zu aktualisieren
                , consumes = MediaType.APPLICATION_JSON_VALUE
                , produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody

    public ResponseEntity<?> update(@RequestBody User newUser, @PathVariable("id") Long userId) {
        log.debug("update() is called");
        User user = userService.getUserById(userId);
        if (user == null) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }

        String detail = null;

        if (newUser == null) {
            detail = "User must not be null";            
        } else if (newUser.getRole() == null) {
            detail = "Role must not be null";
        } else if (newUser.getRole().isEmpty()) {
            detail = "Role must not be empty";
        } else if (newUser.getFirstName() == null) {
            detail = "FirstName must not be null";
        } else if (newUser.getFirstName().isEmpty()) {
            detail = "FirstName must not be empty";
        } else if (newUser.getLastName() == null) {
            detail = "LastName must not be null";
        } else if (newUser.getLastName().isEmpty()) {
            detail = "LastName must not be empty";
        } else if (newUser.getEmail() == null) {
            detail = "Email must not be null";
        } else if (newUser.getEmail().isEmpty()) {
            detail = "Email must not be empty";
        } else if (newUser.getPassword() == null) {
            detail = "Password must not be null";
        } else if (newUser.getPassword().isEmpty()) {
            detail = "Password must not be empty";
        }

        if (detail != null) { //Fehlerbehandlung bei ungültigen Daten
            ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, detail); 
            pd.setInstance(URI.create("/users/" + userId));
            pd.setTitle("JSON Object Error");
            return ResponseEntity.unprocessableEntity().body(pd);
        }
        //Aktualisieren der Benutzerdaten
        user.setRole(newUser.getRole());
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setEmail(newUser.getEmail());
        user.setPassword(newUser.getPassword());
        user = userService.updateUser(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/restful/users/" + user.getId()));
        return new ResponseEntity<User>(user, headers,  HttpStatus.OK);
    }

    @DeleteMapping(value = "/users/{id}", //löschen 
                   produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody

    public ResponseEntity<?> delete(@PathVariable("id") Long userId) {
        log.debug("delete() is called");
        User user = userService.deleteUser(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

}
