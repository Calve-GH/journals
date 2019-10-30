package com.github.calve.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.StringJoiner;

@Entity()
@Table(name = "outgoing")
public class OutgoingMail extends AbstractEntity implements Mail {

    @Column(name = "sent_date", nullable = false)
    private LocalDate outerDate;
    @Column(name = "proceeding", nullable = false)
    private String proceedingNumber;
    @Column(name = "index")
    private Integer genIndex;
    @Column(name = "correspondent", nullable = false)
    private String correspondent;
    @Column(name = "description", nullable = false)
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    private Executor executor;

    public OutgoingMail() {
    }

    public OutgoingMail(Integer id, LocalDate sentDate, String proceeding, Integer index, String correspondent, String description, Executor executor) {
        super.setId(id);
        this.outerDate = sentDate;
        this.proceedingNumber = proceeding;
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

    public String getProceedingNumber() {
        return proceedingNumber;
    }

    public void setProceedingNumber(String proceedingNumber) {
        this.proceedingNumber = proceedingNumber;
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
        return new StringJoiner(", ", OutgoingMail.class.getSimpleName() + "[", "]")
                .add("outerDate=" + outerDate)
                .add("proceedingNumber='" + proceedingNumber + "'")
                .add("outerIndex=" + genIndex)
                .add("correspondent='" + correspondent + "'")
                .add("description='" + description + "'")
                .add("executor=" + executor)
                .toString();
    }
}
