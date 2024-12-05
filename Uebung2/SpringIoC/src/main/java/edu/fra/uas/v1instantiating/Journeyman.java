package edu.fra.uas.v1instantiating;

public class Journeyman {
    Drilling drilling = new Drilling();

    public void performWork() { // das funktion von Geselle ruft den work funktion bei Drilling auf
        drilling.work();
    }
}
