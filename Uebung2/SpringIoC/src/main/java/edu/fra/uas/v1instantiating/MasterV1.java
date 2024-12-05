package edu.fra.uas.v1instantiating;

import org.springframework.stereotype.Component;

@Component
public class MasterV1 {
    Journeyman journeyman = new Journeyman();

    public void delegateWork() { // durch diese Funktion wird die Geselle -> arbeiten funktion aufgerufen.
        journeyman.performWork();
    }
}
