package edu.fra.uas.model;

import java.io.Serializable;
import java.util.HashMap;

import org.slf4j.Logger;

//Serializable Ermöglicht, dass Objekte der Klasse in einen bytestream umgewandelt und z. B. in Dateien gespeichert oder 
//über das Netzwerk übertragen werden können.
public class User implements Serializable {
    
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(User.class);

    private long id;
    private String role;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    
    public User() { // konstrucktor ohne Werte & parameter
        log.debug("User created without values");
    }

    public User(long id, String role, String firstName, String lastName, String email, String password) { // mit Parameter
        log.debug("User created with values + id: " + id + " role: " + role + " firstName: " + firstName + " lastName: " + lastName + " email: " + email + " password: " + password);
        this.id = id;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public long getId() { // setter und getters
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object object) { //Vergleichskriterien: firstName, lastName und email (falls nicht null).
        if (object == null) 
            return false;
        if (object == this) 
            return true;
        if (this.getClass() != object.getClass()) 
            return false;
        if (this.firstName == null) {
            if (((User)object).firstName != null) 
                return false;
        } else if (!this.firstName.equals(((User)object).firstName)) {
            return false;
        }
        if (this.lastName == null) {
            if (((User)object).lastName != null) 
                return false;
        } else if (!this.lastName.equals(((User)object).lastName)) {
            return false;
        }
        if (this.email == null) {
            if (((User)object).email != null) 
                return false;
        } else if (!this.email.equals(((User)object).email)) {
            return false;
        }
        return true;
    }

//Wenn mann in einer HashMap nach einem Schlüssel suchst, wird zuerst der Hash-Wert des Schlüssels berechnet und verwendet,
//um den richtigen Bucket zu finden. Anschließend wird innerhalb dieses Buckets nach dem Element gesucht.

    @Override
    public int hashCode() { //Datenstrukturen wie HashMap speichern und suchen Objekte basierend auf ihren Hash-Werten.
        int hash = 7;  // 7 * 31 -> versucht Kollisionen in Hash-basierten Datenstrukturen zu verringern
        hash = 31 * hash + (this.firstName != null ? this.firstName.hashCode() : 0); // 31 *7 +("Nima".hashcode() = 1) = 217 + 1 =218
       //wenn nicht Null dann führ .hashcode(). wenn firstname null ist wird 0 als Ersatzwert verwendet(Nullzeiger ausanheme verhindern) = hash = 2017
        hash = 31 * hash + (this.lastName != null ? this.lastName.hashCode() : 0); // 31 *7 +("Turk".hashcode() = 1) = 217 + -217 = 0
        hash = 31 * hash + (this.email != null ? this.email.hashCode() : 0);// 31 *7 +("Nima@tmx.de".hashcode() = 1) = 217 + -220  = -3
        return hash; // hashwert = 215
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", role=" + role + ", firstName=" + firstName + ", lastName=" + lastName + ", email="
                + email + ", password=" + password + "]";
    }

}
