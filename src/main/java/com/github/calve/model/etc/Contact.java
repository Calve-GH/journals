package com.github.calve.model.etc;

import com.github.calve.model.AbstractEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.StringJoiner;

@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity
@Table(name = "contacts")
public class Contact extends AbstractEntity {
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "email")
    private String email;

    public Contact() {
    }

    public Contact(Integer id, String alias, String email) {
        super.setId(id);
        this.name = alias;
        this.email = email;
    }

    public Contact(String alias, String email) {
        this.name = alias;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                .add("id='" + super.getId() + "'")
                .add("alias='" + name + "'")
                .add("email='" + email + "'")
                .toString();
    }
}