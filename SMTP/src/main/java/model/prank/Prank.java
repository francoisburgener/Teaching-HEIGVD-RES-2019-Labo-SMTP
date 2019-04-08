package model.prank;

import model.mail.*;

import java.util.LinkedList;

public class Prank {
    Victim sender;
    LinkedList<Victim> receptors;
    String message;


    public Prank(Victim sender, LinkedList<Victim> receptors, String message) {
        this.sender = sender;
        this.receptors = receptors;
        this.message = message;
    }

    public Mail createMail(){
        LinkedList<String> addressesTo = new LinkedList<String>();
        for(Victim v : receptors){
            addressesTo.add(v.getMailAddress());
        }
        int splitter = message.indexOf("\r\n");
        String subject = message.substring(0, splitter);
        String body = message.substring(splitter+1);

        return new Mail(sender.getMailAddress(), addressesTo, body, subject);
    }

}
