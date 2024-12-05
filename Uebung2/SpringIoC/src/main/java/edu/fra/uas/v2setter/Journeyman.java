package edu.fra.uas.v2setter;

public class Journeyman {
    Work work;

    public Journeyman setWork(Work work) {
        this.work = work;
        return this; //Returning this allows the caller to chain another method on the same object.
    }

    public void performWork() {
        work.doWork();
    }
}
