package edu.fra.uas.v3autowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component //Spring verwaltet eine Instanz dieser Klasse als Bean im Spring Application Context
    	    //Spring erstellt automatisch ein Objekt dieser Klasse und macht es für andere Klassen verfügbar, die es injizieren möchten.
public class Journeyman { // hat ein Abhängigkeit von klasse Work
@Autowired //Sagt Spring, dass die Abhängigkeit für das Feld work automatisch bereitgestellt werden soll.
//Verwendet Dependency Injection, um Objekte automatisch zu instanziieren und bereitzustellen, anstatt manuell zu erstellen.
@Qualifier("pleasePaint") //Wenn mehrere Beans vom Typ Work existieren, wird mit @Qualifier
//angegeben, welche spezifische Bean injiziert werden soll.Hier wird eine Bean mit dem Namen pleasePaint injiziert.
   //
Work work; // Ermöglicht es Journeyman, auf die Methoden von Work zuzugreifen.

    public void performWork() {
        work.doWork();
   }
}
