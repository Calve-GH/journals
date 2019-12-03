package com.github.calve.to.excel;

import com.github.calve.to.etc.Remain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;
import java.util.StringJoiner;

import static com.github.calve.util.DateTimeUtil.*;

public class MailTo extends BaseMailTo {

    @NotNull(message = "должна быть задана")
    private LocalDate incomeDate;
    @NotBlank(message = "не может быть пустым")
    private String incomeIndex;
    private LocalDate doneDate;
    private String doneIndex;
    private String doneResult;
    private String proceedingNumber;
    private String debtor;
    private LocalDate workDate;
    private String workIndex;
    private String authority;

    private Remain remain;

    public MailTo() {
    }

    public MailTo(Integer id, LocalDate incomeDate, String incomeIndex, String correspondent,
                  LocalDate outerDate, String outerIndex, String description, String executor,
                  LocalDate doneDate, String doneIndex, String doneResult, String proceedingNumber,
                  String debtor, LocalDate workDate, String workIndex, String authority, boolean generics) {
        this(id, incomeDate, incomeIndex, correspondent, outerDate, outerIndex, description, executor, doneDate,
                doneIndex, proceedingNumber, workDate, workIndex, authority, generics);
        this.doneResult = doneResult;
        this.debtor = debtor;
    }

    public MailTo(Integer id, LocalDate incomeDate, String incomeIndex, String correspondent, LocalDate outerDate,
                  String outerIndex, String description, String executor, LocalDate doneDate, String doneIndex,
                  boolean generics) {
        super.setId(id);
        this.incomeDate = incomeDate;
        this.incomeIndex = incomeIndex;
        this.correspondent = correspondent;
        this.outerDate = outerDate;
        this.outerIndex = outerIndex;
        this.description = description;
        this.executor = executor;
        this.doneDate = doneDate;
        this.doneIndex = doneIndex;
        //boilerplate code
        //refactoring
        if (Objects.nonNull(this.incomeDate) && Objects.isNull(this.doneDate)) {
            if (!generics) {
                this.remain = initRemains(this.incomeDate, getLastDay(this.incomeDate, false));
                this.excess = initExcess(this.incomeDate, getLastDay(this.incomeDate, false));
            } else {
                this.remain = initRemains(this.incomeDate, getLastDay(this.incomeDate, true));
                this.excess = initExcess(this.incomeDate, getLastDay(this.incomeDate, true));
            }
        } else {
            this.remain = Remain.DONE_REMAIN;
        }
    }

    public MailTo(Integer id, LocalDate incomeDate, String incomeIndex, String correspondent, LocalDate outerDate,
                  String outerIndex, String description, String executor, LocalDate doneDate, String doneIndex,
                  String doneResult, boolean generics) {
        this(id, incomeDate, incomeIndex, correspondent, outerDate, outerIndex, description, executor, doneDate,
                doneIndex, generics);
        this.doneResult = doneResult;
    }

    public MailTo(Integer id, LocalDate incomeDate, String incomeIndex, String correspondent, LocalDate outerDate,
                  String outerIndex, String description, String executor, String proceedingNumber, String debtor) {
        super.setId(id);
        this.incomeDate = incomeDate;
        this.incomeIndex = incomeIndex;
        this.correspondent = correspondent;
        this.outerDate = outerDate;
        this.outerIndex = outerIndex;
        this.description = description;
        this.executor = executor;
        this.proceedingNumber = proceedingNumber;
        this.debtor = debtor;
    }

    public MailTo(Integer id, LocalDate incomeDate, String incomeIndex, String correspondent, LocalDate outerDate,
                  String outerIndex, String description, String executor, LocalDate doneDate, String doneIndex,
                  String proceedingNumber, LocalDate workDate, String workIndex, String authority, boolean generics) {
        this(id, incomeDate, incomeIndex, correspondent, outerDate, outerIndex, description, executor, doneDate,
                doneIndex, generics);
        this.proceedingNumber = proceedingNumber;
        this.workDate = workDate;
        this.workIndex = workIndex;
        this.authority = authority;
    }

    public Remain getRemain() {
        return remain;
    }

    public void setRemain(Remain remain) {
        this.remain = remain;
    }

    public boolean isExcess() {
        return excess;
    }

    public void setExcess(boolean excess) {
        this.excess = excess;
    }

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

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public LocalDate getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(LocalDate doneDate) {
        this.doneDate = doneDate;
    }

    public String getDoneIndex() {
        return doneIndex;
    }

    public void setDoneIndex(String doneIndex) {
        this.doneIndex = doneIndex;
    }

    public String getDoneResult() {
        return doneResult;
    }

    public void setDoneResult(String doneResult) {
        this.doneResult = doneResult;
    }

    public String getProceedingNumber() {
        return proceedingNumber;
    }

    public void setProceedingNumber(String proceedingNumber) {
        this.proceedingNumber = proceedingNumber;
    }

    public String getDebtor() {
        return debtor;
    }

    public void setDebtor(String debtor) {
        this.debtor = debtor;
    }

    public LocalDate getWorkDate() {
        return workDate;
    }

    public void setWorkDate(LocalDate workDate) {
        this.workDate = workDate;
    }

    public String getWorkIndex() {
        return workIndex;
    }

    public void setWorkIndex(String workIndex) {
        this.workIndex = workIndex;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MailTo mailTo = (MailTo) o;
        return Objects.equals(incomeDate, mailTo.incomeDate) &&
                Objects.equals(incomeIndex, mailTo.incomeIndex) &&
                Objects.equals(correspondent, mailTo.correspondent) &&
                Objects.equals(outerDate, mailTo.outerDate) &&
                Objects.equals(outerIndex, mailTo.outerIndex) &&
                Objects.equals(description, mailTo.description) &&
                Objects.equals(executor, mailTo.executor) &&
                Objects.equals(doneDate, mailTo.doneDate) &&
                Objects.equals(doneIndex, mailTo.doneIndex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(incomeDate, incomeIndex, correspondent, outerDate, outerIndex, description, executor, doneDate, doneIndex);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MailTo.class.getSimpleName() + "[", "]")
                .add("incomeDate=" + incomeDate)
                .add("incomeIndex='" + incomeIndex + "'")
                .add("doneDate=" + doneDate)
                .add("doneIndex='" + doneIndex + "'")
                .add("doneResult='" + doneResult + "'")
                .add("proceedingNumber='" + proceedingNumber + "'")
                .add("debtor='" + debtor + "'")
                .add("workDate=" + workDate)
                .add("workIndex='" + workIndex + "'")
                .add("authority='" + authority + "'")
                .add("remain=" + remain)
                .add("outerDate=" + outerDate)
                .add("outerIndex='" + outerIndex + "'")
                .add("correspondent='" + correspondent + "'")
                .add("description='" + description + "'")
                .add("executor='" + executor + "'")
                .add("excess=" + excess)
                .add("id=" + id)
                .toString();
    }
}