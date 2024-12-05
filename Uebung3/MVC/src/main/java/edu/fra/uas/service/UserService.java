package edu.fra.uas.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.fra.uas.model.User;
import edu.fra.uas.repository.UserRepository;

/**
 * This class represents the service for the user.
 */
@Service
public class UserService {
    
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(UserService.class);

    @Autowired //durch die Spring-Annotation @Autowired automatisch injiziert
    //Spring übernimmt die Verwaltung der Repository Verbindung, sodass UserService darauf zugreifen kann
    private UserRepository userRepository;
    
    private long nextUserId = 1; // jeder user hat ein UserID zugewiesen

    //Benutzer wird dann in das userRepository eingefügt
    //wobei der id des Benutzers als Schlüssel und der Benutzer selbst als Wert gespeichert wird.
    //getId() Methode von User
    public User createUser(User user) {
        log.debug("createUser: " + user);
        user.setId(nextUserId++);
        userRepository.put(user.getId(), user); // Hashmap
        return user;
    }

    public Iterable<User> getAllUsers() { //Liste von allen Benutzern
        log.debug("getAllUsers");
        return userRepository.values();
    }

    public User getUserById(long id) {
        log.debug("getUser: " + id);
        return userRepository.get(id); // id ist der Schlüssel von Hashmap
    }

    public User updateUser(User user) {
        log.debug("updateUser: " + user);
        userRepository.put(user.getId(), user); // wird durch schlüssel und ein neue User objekt erstetz(updatet)
        return user;
    }

    public void deleteUser(long id) {
        log.debug("deleteUser: " + id);
        userRepository.remove(id);
    }

}
