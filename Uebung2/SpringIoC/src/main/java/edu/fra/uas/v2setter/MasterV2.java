package edu.fra.uas.v2setter;

public class MasterV2 {
    Journeyman journeyman;

    public void setJourneymanAndWork(Journeyman journeyman, Work work) { // so ein Art wie Setter aber dafür muss man ein Instanz von Masterv2 initialsieren sonst funktiert nicht!! sehe Main
        this.journeyman = journeyman; // zuweisung
        this.journeyman.setWork(work);
        System.out.println("Second methode!: Set JourneymanAndWork Method is running!"); // direkt den workfuntion aufrufen
    }

    public void delegateWork() {
        journeyman.performWork();
    }
}
