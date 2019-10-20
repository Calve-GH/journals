package com.github.calve.util.builders;

import com.github.calve.to.MailTo;

import java.time.LocalDate;
import java.util.StringJoiner;

public class ToBuilder {

    private Integer id;
    private LocalDate incomeDate;
    private String incomeIndex;
    private String correspondent;
    private LocalDate outerDate;
    private String outerIndex;
    private String description;
    private String executor;
    private LocalDate doneDate;
    private String doneIndex;
    private String doneResult;
    private LocalDate workDate;
    private String workIndex;
    private String authority;
    private String proceedingNumber;
    private String debtor;

    public MailTo getMailTo() {
        return new MailTo(this.id, this.incomeDate, this.incomeIndex, this.correspondent, this.outerDate,
                this.outerIndex, this.description, this.executor, this.doneDate, this.doneIndex, this.doneResult,
                this.proceedingNumber, this.debtor, this.workDate, this.workIndex, this.authority);
    }

    public MailTo getRequest() {
        return new MailTo(this.id, this.incomeDate, this.incomeIndex, this.correspondent,
                this.outerDate, this.outerIndex, this.description, this.executor,
                this.doneDate, this.doneIndex);
    }

    public ToBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public ToBuilder setIncomeDate(LocalDate incomeDate) {
        this.incomeDate = incomeDate;
        return this;
    }

    public ToBuilder setIncomeIndex(String incomeIndex) {
        this.incomeIndex = incomeIndex;
        return this;
    }

    public ToBuilder setCorrespondent(String correspondent) {
        this.correspondent = correspondent;
        return this;
    }

    public ToBuilder setOuterDate(LocalDate outerDate) {
        this.outerDate = outerDate;
        return this;
    }

    public ToBuilder setOuterIndex(String outerIndex) {
        this.outerIndex = outerIndex;
        return this;
    }

    public ToBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public ToBuilder setExecutor(String executor) {
        this.executor = executor;
        return this;
    }

    public ToBuilder setDoneDate(LocalDate doneDate) {
        this.doneDate = doneDate;
        return this;
    }

    public ToBuilder setDoneIndex(String doneIndex) {
        this.doneIndex = doneIndex;
        return this;
    }

    public ToBuilder setDoneResult(String doneResult) {
        this.doneResult = doneResult;
        return this;
    }

    public ToBuilder setWorkDate(LocalDate workDate) {
        this.workDate = workDate;
        return this;
    }

    public ToBuilder setWorkIndex(String workIndex) {
        this.workIndex = workIndex;
        return this;
    }

    public ToBuilder setAuthority(String authority) {
        this.authority = authority;
        return this;
    }

    public ToBuilder setProceedingNumber(String proceedingNumber) {
        this.proceedingNumber = proceedingNumber;
        return this;
    }

    public ToBuilder setDebtor(String debtor) {
        this.debtor = debtor;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ToBuilder.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("incomeDate=" + incomeDate)
                .add("incomeIndex='" + incomeIndex + "'")
                .add("correspondent='" + correspondent + "'")
                .add("outerDate=" + outerDate)
                .add("outerIndex='" + outerIndex + "'")
                .add("description='" + description + "'")
                .add("executor='" + executor + "'")
                .add("doneDate=" + doneDate)
                .add("doneIndex='" + doneIndex + "'")
                .add("doneResult='" + doneResult + "'")
                .add("workDate=" + workDate)
                .add("workIndex='" + workIndex + "'")
                .add("authority='" + authority + "'")
                .add("proceedingNumber='" + proceedingNumber + "'")
                .add("debtor='" + debtor + "'")
                .toString();
    }
}
