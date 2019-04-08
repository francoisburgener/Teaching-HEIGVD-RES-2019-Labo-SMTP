package model.mail;

import lombok.Getter;

public class Victim {

    @Getter
    private String mailAddress;

    public Victim(String mailAddress) {
        this.mailAddress = mailAddress;
    }
}
