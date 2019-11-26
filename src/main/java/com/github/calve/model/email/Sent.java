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
    
    @Column(name = "sent_date", nullable = false)
    private LocalDate incomeDate;
    @ManyToOne(fetch = FetchType.EAGER)
    private Contact contact;
    @Column(name = "description")
    private String description;

    // TODO: 25.11.2019 thinking about email string content;
    public Sent() {
    }

    public Sent(Integer id, LocalDate sentDate, Contact contact, String description) {
        super.setId(id);
        this.incomeDate = sentDate;
        this.contact = contact;
        this.description = description;
    }

    public LocalDate getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(LocalDate incomeDate) {
        this.incomeDate = incomeDate;
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

    @Override
    public String toString() {
        return new StringJoiner(", ", Sent.class.getSimpleName() + "[", "]")
                .add("sentDate=" + incomeDate)
                .add("contact=" + contact)
                .add("description='" + description + "'")
                .toString();
    }
}
