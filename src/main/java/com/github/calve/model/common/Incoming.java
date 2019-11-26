package com.github.calve.model.common;

import com.github.calve.model.AbstractEntity;
import com.github.calve.model.etc.Executor;
import com.github.calve.model.Mail;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.StringJoiner;

@Entity
@Table(name = "incoming")
public class Incoming extends AbstractEntity implements Mail {
    @Column(name = "index", nullable = false)
    private Integer genIndex;
    @Column(name = "reg_date", nullable = false)
    private LocalDate regDate;
    @Column(name = "description")
    private String description;
    @Column(name = "debtor", nullable = false)
    private String debtor;
    @ManyToOne(fetch = FetchType.EAGER)
    private Executor executor;

    public Incoming() {
    }

    public Incoming(Integer id, Integer genIndex, LocalDate regDate, String description, String debtor, Executor executor) {
        super.setId(id);
        this.genIndex = genIndex;
        this.regDate = regDate;
        this.description = description;
        this.debtor = debtor;
        this.executor = executor;
    }

    public Integer getGenIndex() {
        return genIndex;
    }

    public void setGenIndex(Integer genIndex) {
        this.genIndex = genIndex;
    }

    public LocalDate getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDate regDate) {
        this.regDate = regDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDebtor() {
        return debtor;
    }

    public void setDebtor(String debtor) {
        this.debtor = debtor;
    }

    public Executor getExecutor() {
        return executor;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Incoming.class.getSimpleName() + "[", "]")
                .add("genIndex=" + genIndex)
                .add("regDate=" + regDate)
                .add("description='" + description + "'")
                .add("debtor='" + debtor + "'")
                .add("executor=" + executor)
                .toString();
    }
}
