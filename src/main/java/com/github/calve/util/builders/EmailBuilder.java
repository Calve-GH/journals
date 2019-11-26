package com.github.calve.util.builders;

import com.github.calve.model.email.Inbox;
import com.github.calve.model.email.Sent;
import com.github.calve.model.etc.Contact;

import java.time.LocalDate;

public class EmailBuilder {

    private final static LocalDate NOW = LocalDate.now();

    private Integer id;
    private Integer genIndex;
    private Contact contact;
    private LocalDate date = NOW;
    private Boolean answer;
    private String description;

    public EmailBuilder() {
    }

    public Inbox getInbox() {
        return new Inbox(this.id, this.genIndex, this.contact, this.date, this.answer, this.description);
    }

    public Sent getSent() {
        return new Sent(this.id, this.date, this.contact, this.description);
    }

    public EmailBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public EmailBuilder setGenIndex(Integer genIndex) {
        this.genIndex = genIndex;
        return this;
    }

    public EmailBuilder setContact(Contact contact) {
        this.contact = contact;
        return this;
    }

    public EmailBuilder setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public EmailBuilder setAnswer(Boolean answer) {
        this.answer = answer;
        return this;
    }

    public EmailBuilder setDescription(String description) {
        this.description = description;
        return this;
    }
}
