package model.mail;

import lombok.Getter;

import java.util.LinkedList;

public class Mail {
    @Getter
    private String from, body, subject;

    @Getter
    private LinkedList<String> to;

    public Mail(String from, LinkedList<String> to, String body, String subject) {
        this.from = from;
        this.to = to;
        this.body = body;
        this.subject = subject;
    }
}
