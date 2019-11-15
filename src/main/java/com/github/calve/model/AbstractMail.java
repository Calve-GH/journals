package com.github.calve.model;

import javax.persistence.*;
import java.time.LocalDate;

@MappedSuperclass
@Access(AccessType.FIELD)
public class AbstractMail extends AbstractEntity implements Mail {
    @Column(name = "income_date", nullable = false) // TODO: 26.09.2019 rename
    private LocalDate incomeDate;
    @Column(name = "income_index", nullable = false)
    private String incomeIndex;
    @Column(name = "correspondent", nullable = false)
    private String correspondent;
    @Column(name = "outer_date", nullable = false) // TODO: 26.09.2019 rename
    private LocalDate outerDate;
    @Column(name = "outer_index", nullable = false) // TODO: 26.09.2019 rename
    private String outerIndex;
    @Column(name = "description", nullable = false)
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    private Executor executor;

    public LocalDate getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(LocalDate incomeDate) {
        this.incomeDate = incomeDate;
    }

    public String getIncomeIndex() {
        return incomeIndex;
    }

    public void setIncomeIndex(String incomeIndex) {
        this.incomeIndex = incomeIndex;
    }

    public String getCorrespondent() {
        return correspondent;
    }

    public void setCorrespondent(String correspondent) {
        this.correspondent = correspondent;
    }

    public LocalDate getOuterDate() {
        return outerDate;
    }

    public void setOuterDate(LocalDate outerDate) {
        this.outerDate = outerDate;
    }

    public String getOuterIndex() {
        return outerIndex;
    }

    public void setOuterIndex(String outerIndex) {
        this.outerIndex = outerIndex;
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
