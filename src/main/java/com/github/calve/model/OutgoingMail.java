package com.github.calve.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity()
@Table(name = "outgoing")
public class OutgoingMail extends AbstractEntity {
    @Column(name = "sent_date", nullable = false)
    private LocalDate sentDate;
    @Column(name = "proceeding", nullable = false)
    private String proceeding;
    @Column(name = "index", nullable = false)
    private Integer index;
    @Column(name = "correspondent", nullable = false)
    private String correspondent;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "executor", nullable = false)
    private Executor executor;

    public LocalDate getSentDate() {
        return sentDate;
    }

    public void setSentDate(LocalDate sentDate) {
        this.sentDate = sentDate;
    }

    public String getProceeding() {
        return proceeding;
    }

    public void setProceeding(String proceeding) {
        this.proceeding = proceeding;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
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
}
