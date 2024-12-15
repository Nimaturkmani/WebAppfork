package edu.fra.uas.model;

import org.springframework.hateoas.RepresentationModel;


//Die Klasse UserDTO ist ein Data Transfer Object (DTO), das für den Austausch von Benutzerdaten zwischen
//verschiedenen Schichten einer Anwendung oder zwischen Systemen verwendet wird. Es wird in der Regel genutzt, um:
  //Daten-Modelle zu vereinfachen, z. B. durch das Entfernen sensibler Felder wie Passwörter.
    //Effiziente Kommunikation zwischen der API und ihren Clients zu ermöglichen, indem nur die benötigten Felder übertragen werden.
        //HATEOAS-Funktionalität (Hypermedia As The Engine Of Application State) zu unterstützen,
       //indem es von RepresentationModel<UserDTO> erbt.
public class UserDTO extends RepresentationModel<UserDTO> {
    
    private long id;
    private String firstName;
    private String lastName;
    private String email;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
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

}
