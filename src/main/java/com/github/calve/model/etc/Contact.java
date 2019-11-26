package com.github.calve.model.etc;

import com.github.calve.model.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.StringJoiner;

@Entity
@Table(name = "contacts")
public class Contact extends AbstractEntity {
    @Column(name = "alias", nullable = false)
    private String alias;
    @Column(name = "email")
    private String email;

    public Contact() {
    }

    public Contact(String alias, String email) {
        this.alias = alias;
        this.email = email;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Contact.class.getSimpleName() + "[", "]")
                .add("alias='" + alias + "'")
                .add("email='" + email + "'")
                .toString();
    }
}