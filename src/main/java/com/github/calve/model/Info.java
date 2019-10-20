package com.github.calve.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Entity()
@Table(name = "info")
public class Info extends AbstractMail {
    @Column(name = "done_date", nullable = false)
    private LocalDate doneDate;
    @Column(name = "done_index", nullable = false)
    private String doneIndex;
    @Column(name = "done_result", nullable = false)
    private String doneResult;


    // TODO: 19.10.2019 wrong data fields
    public Info() {
    }

    public Info(Integer id, LocalDate incomeDate, String incomeIndex, String corr, LocalDate outerDate,
                String outerIndex, String description, Executor executor, LocalDate doneDate, String doneIndex, String doneResult) {
        super.setId(id);
        super.setIncomeDate(incomeDate);
        super.setIncomeIndex(incomeIndex);
        super.setCorrespondent(corr);
        super.setOuterDate(outerDate);
        super.setOuterIndex(outerIndex);
        super.setDescription(description);
        super.setExecutor(executor);
        this.doneDate = doneDate;
        this.doneIndex = doneIndex;
        this.doneResult = doneResult;
    }

    @Override
    public String getDoneResult() {
        return doneResult;
    }

    public void setDoneResult(String doneResult) {
        this.doneResult = doneResult;
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
}
