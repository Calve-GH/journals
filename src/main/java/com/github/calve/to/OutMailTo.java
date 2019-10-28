package com.github.calve.to;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.StringJoiner;

public class OutMailTo extends BaseTo implements Serializable {
    @NotNull(message = "должна быть задана")
    private LocalDate outerDate;
    @NotNull(message = "должн быть задан")
    private String proceedingNumber;
    private Integer outerIndex;
    @NotBlank(message = "не может быть пустым")
    private String correspondent;
    private String description;
    @NotBlank(message = "не может быть пустым")
    private String executor;
    private boolean excess = false;

    public OutMailTo() {
    }

    public OutMailTo(Integer id, LocalDate outerDate, String proceedingNumber,
                     Integer outerIndex, String correspondent,
                     String description, String executor) {
        super(id);
        this.outerDate = outerDate;
        this.proceedingNumber = proceedingNumber;
        this.outerIndex = outerIndex;
        this.correspondent = correspondent;
        this.description = description;
        this.executor = executor;
    }

    public LocalDate getOuterDate() {
        return outerDate;
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

    public Integer getOuterIndex() {
        return outerIndex;
    }

    public void setOuterIndex(Integer outerIndex) {
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

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public boolean isExcess() {
        return excess;
    }

    public void setExcess(boolean excess) {
        this.excess = excess;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", OutMailTo.class.getSimpleName() + "[", "]")
                .add("outerDate=" + outerDate)
                .add("proceedingNumber='" + proceedingNumber + "'")
                .add("outerIndex=" + outerIndex)
                .add("correspondent='" + correspondent + "'")
                .add("description='" + description + "'")
                .add("executor='" + executor + "'")
                .add("excess=" + excess)
                .toString();
    }
}
