package model.mail;

import lombok.Getter;

import java.util.LinkedList;

public class Group {
    @Getter
    private LinkedList<Victim> victims = new LinkedList<Victim>();

    /**
     * Adds victim to the group
     * @param v The victim we add
     */
    public void addVictim(Victim v){
        victims.add(v);
    }

}
