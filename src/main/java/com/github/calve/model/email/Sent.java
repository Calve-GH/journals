package com.github.calve.model.email;

import com.github.calve.model.AbstractEntity;
import com.github.calve.model.Mail;
import com.github.calve.model.etc.Contact;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.StringJoiner;

@Entity
@Table(name = "sent")
public class Sent extends AbstractEntity implements Mail {
    
    @Column(name = "reg_date", nullable = false)
    private LocalDate regDate;
    @ManyToOne(fetch = FetchType.EAGER)
    private Contact contact;
    @Column(name = "description")
    private String description;
    @Column(name = "auto")
    private Boolean auto;

    // TODO: 25.11.2019 thinking about email string content;
    public Sent() {
    }

    public Sent(Integer id, LocalDate sentDate, Contact contact, String description) {
        super.setId(id);
        this.regDate = sentDate;
        this.contact = contact;
        this.description = description;
    }

    public LocalDate getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDate regDate) {
        this.regDate = regDate;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAuto() {
        return auto;
    }

    public void setAuto(Boolean auto) {
        this.auto = auto;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Sent.class.getSimpleName() + "[", "]")
                .add("sentDate=" + regDate)
                .add("contact=" + contact)
                .add("description='" + description + "'")
                .toString();
    }
}
