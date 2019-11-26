package com.github.calve.model.email;

import com.github.calve.model.AbstractEntity;
import com.github.calve.model.Mail;
import com.github.calve.model.etc.Contact;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.StringJoiner;

@Entity
@Table(name = "inbox")
public class Inbox extends AbstractEntity implements Mail {
    @Column(name = "index", nullable = false)
    private Integer genIndex;
    @ManyToOne(fetch = FetchType.EAGER)
    private Contact contact;
    @Column(name = "income_date", nullable = false)
    private LocalDate incomeDate;
    @Column(name = "answer")
    private Boolean answer;
    @Column(name = "description")
    private String description;

    public Inbox() {
    }

    public Inbox(Integer id, Integer genIndex, Contact contact, LocalDate incomeDate, Boolean answer, String description) {
        super.setId(id);
        this.genIndex = genIndex;
        this.contact = contact;
        this.incomeDate = incomeDate;
        this.answer = answer;
        this.description = description;
    }

    public Integer getGenIndex() {
        return genIndex;
    }

    public void setGenIndex(Integer genIndex) {
        this.genIndex = genIndex;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @Override
    public LocalDate getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(LocalDate incomeDate) {
        this.incomeDate = incomeDate;
    }

    public Boolean getAnswer() {
        return answer;
    }

    public void setAnswer(Boolean answer) {
        this.answer = answer;
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
        return new StringJoiner(", ", Inbox.class.getSimpleName() + "[", "]")
                .add("genIndex=" + genIndex)
                .add("contact=" + contact)
                .add("incomeDate=" + incomeDate)
                .add("answer=" + answer)
                .add("description='" + description + "'")
                .toString();
    }
}
