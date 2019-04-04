package model.mail;

import lombok.Getter;

public class Mail {
    @Getter
    private String from, to, body, subject;

    public Mail(String from, String to, String body, String subject) {
        this.from = from;
        this.to = to;
        this.body = body;
        this.subject = subject;
    }
}
