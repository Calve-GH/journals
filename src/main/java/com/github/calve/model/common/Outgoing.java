package com.github.calve.model.common;

import com.github.calve.model.AbstractEntity;
import com.github.calve.model.etc.Executor;
import com.github.calve.model.Mail;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.StringJoiner;

@Entity()
@Table(name = "outgoing")
public class Outgoing extends AbstractEntity implements Mail {
    @Column(name = "sent_date", nullable = false)
    private LocalDate outerDate;
    @Column(name = "proceeding", nullable = false)
    private String outerIndex;
    @Column(name = "index", nullable = false)
    private Integer genIndex;
    @Column(name = "correspondent", nullable = false)
    private String correspondent;
    @Column(name = "description")
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    private Executor executor;

    public Outgoing() {
    }

    public Outgoing(Integer id, LocalDate sentDate, String outerIndex, Integer index, String correspondent, String description, Executor executor) {
        super.setId(id);
        this.outerDate = sentDate;
        this.outerIndex = outerIndex;
        this.genIndex = index;
        this.correspondent = correspondent;
        this.description = description;
        this.executor = executor;
    }

    public LocalDate getOuterDate() {
        return outerDate;
    }

    public Integer getGenIndex() {
        return genIndex;
    }

    public void setGenIndex(Integer genIndex) {
        this.genIndex = genIndex;
    }

    public void setOuterDate(LocalDate outerDate) {
        this.outerDate = outerDate;
    }

    @Override
    public String getOuterIndex() {
        return outerIndex;
    }

    public void setOuterIndex(String outerIndex) {
        this.outerIndex = outerIndex;
    }

    public String getCorrespondent() {
        return correspondent;
    }

    public void setCorrespondent(String correspondent) {
        this.correspondent = correspondent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Executor getExecutor() {
        return executor;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Outgoing.class.getSimpleName() + "[", "]")
                .add("outerDate=" + outerDate)
                .add("outerIndex='" + outerIndex + "'")
                .add("index=" + genIndex)
                .add("correspondent='" + correspondent + "'")
                .add("description='" + description + "'")
                .add("executor=" + executor)
                .toString();
    }
}
