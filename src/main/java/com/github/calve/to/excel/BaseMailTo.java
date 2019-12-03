package com.github.calve.to.excel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.StringJoiner;

public class BaseMailTo extends BaseTo implements Serializable {
    @NotNull(message = "должна быть задана")
    protected LocalDate outerDate;
    @NotNull(message = "должн быть задан")
    protected String outerIndex;
    private Integer genIndex;
    @NotBlank(message = "не может быть пустым")
    protected String correspondent;
    protected String description;
    @NotBlank(message = "не может быть пустым")
    protected String executor;
    protected boolean excess = false;

    public BaseMailTo() {
    }

    public BaseMailTo(Integer id, LocalDate outerDate, String outerIndex,
                      Integer genIndex, String correspondent,
                      String description, String executor) {
        super(id);
        this.outerDate = outerDate;
        this.outerIndex = outerIndex;
        this.genIndex = genIndex;
        this.correspondent = correspondent;
        this.description = description;
        this.executor = executor;
    }

    public void setId(Integer id) {
        super.setId(id);
    }

    public Integer getId() {
        return super.getId();
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

    public Integer getGenIndex() {
        return genIndex;
    }

    public void setGenIndex(Integer genIndex) {
        this.genIndex = genIndex;
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
        return new StringJoiner(",\n", BaseMailTo.class.getSimpleName() + "[", "]")
                .add("outerDate=" + outerDate)
                .add("outerIndex='" + outerIndex + "'")
                .add("genIndex=" + genIndex)
                .add("correspondent='" + correspondent + "'")
                .add("description='" + description + "'")
                .add("executor='" + executor + "'")
                .add("excess=" + excess)
                .toString();
    }
}
