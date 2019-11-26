package com.github.calve.util.builders;

import com.github.calve.model.common.Incoming;
import com.github.calve.model.common.Outgoing;
import com.github.calve.model.etc.Executor;
import com.github.calve.model.journal.*;

import java.time.LocalDate;

public class MailBuilder {

    private final static LocalDate NOW = LocalDate.now();

    private Integer id;
    private LocalDate incomeDate = NOW;
    private String incomeIndex;
    private String correspondent;
    private LocalDate outerDate;
    private String outerIndex;
    private String description;
    private Executor executor;
    private LocalDate doneDate;
    private String doneIndex;
    private String doneResult;
    private LocalDate workDate;
    private String workIndex;
    private String authority;
    private String proceedingNumber;
    private String debtor;
    private Integer genIndex;

    public Request getRequest() {
        return new Request(this.id, this.incomeDate, this.incomeIndex, this.correspondent, this.outerDate,
                this.outerIndex, this.description, this.executor, this.doneDate, this.doneIndex);
    }

    public Generic getGeneric() {
        return new Generic(this.id, this.incomeDate, this.incomeIndex, this.correspondent, this.outerDate,
                this.outerIndex, this.description, this.executor, this.doneDate, this.doneIndex);
    }

    public Complaint getComplaint() {
        return new Complaint(this.id, this.incomeDate, this.incomeIndex, this.correspondent, this.outerDate,
                this.outerIndex, this.description, this.executor, this.doneDate, this.doneIndex, this.doneResult);
    }

    public Foreigner getForeigner() {
        return new Foreigner(this.id, this.incomeDate, this.incomeIndex, this.correspondent, this.outerDate,
                this.outerIndex, this.description, this.executor, this.proceedingNumber, this.debtor);
    }

    public Application getApplication() {
        return new Application(this.id, this.incomeDate, this.incomeIndex, this.correspondent, this.outerDate,
                this.outerIndex, this.description, this.executor, this.doneDate, this.doneIndex, this.workDate,
                this.workIndex, this.authority, this.proceedingNumber);
    }

    public Info getInfo() {
        return new Info(this.id, this.incomeDate, this.incomeIndex, this.correspondent, this.outerDate,
                this.outerIndex, this.description, this.executor, this.doneDate, this.doneIndex, this.doneResult);
    }

    public Outgoing getOutgoing() {
        return new Outgoing(this.id, outerDate, this.outerIndex, this.genIndex,
                this.correspondent, this.description, this.executor);
    }

    public Incoming getIncoming() {
        return new Incoming(this.id, this.genIndex, this.outerDate,
                this.description, this.correspondent, this.executor);
    }

    public MailBuilder setGenIndex(Integer genIndex) {
        this.genIndex = genIndex;
        return this;
    }

    public MailBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public MailBuilder setIncomeDate(LocalDate incomeDate) {
        this.incomeDate = incomeDate;
        return this;
    }

    public MailBuilder setIncomeIndex(String incomeIndex) {
        this.incomeIndex = incomeIndex;
        return this;
    }

    public MailBuilder setCorrespondent(String correspondent) {
        this.correspondent = correspondent;
        return this;
    }

    public MailBuilder setOuterDate(LocalDate outerDate) {
        this.outerDate = outerDate;
        return this;
    }

    public MailBuilder setOuterIndex(String outerIndex) {
        this.outerIndex = outerIndex;
        return this;
    }

    public MailBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public MailBuilder setExecutor(Executor executor) {
        this.executor = executor;
        return this;
    }

    public MailBuilder setDoneDate(LocalDate doneDate) {
        this.doneDate = doneDate;
        return this;
    }

    public MailBuilder setDoneIndex(String doneIndex) {
        this.doneIndex = doneIndex;
        return this;
    }

    public MailBuilder setDoneResult(String doneResult) {
        this.doneResult = doneResult;
        return this;
    }

    public MailBuilder setWorkDate(LocalDate workDate) {
        this.workDate = workDate;
        return this;
    }

    public MailBuilder setWorkIndex(String workIndex) {
        this.workIndex = workIndex;
        return this;
    }

    public MailBuilder setAuthority(String authority) {
        this.authority = authority;
        return this;
    }

    public MailBuilder setProceedingNumber(String proceedingNumber) {
        this.proceedingNumber = proceedingNumber;
        return this;
    }

    public MailBuilder setDebtor(String debtor) {
        this.debtor = debtor;
        return this;
    }
}

