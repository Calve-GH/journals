package com.github.calve.model.journal;

import com.github.calve.model.etc.Executor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity()
@Table(name = "generics")
public class Generic extends IncomingMail {
    @Column(name = "done_date", nullable = false)
    private LocalDate doneDate;
    @Column(name = "done_index", nullable = false)
    private String doneIndex;

    public Generic() {
    }

    public Generic(Integer id, LocalDate incomeDate, String incomeIndex, String corr, LocalDate outerDate,
                   String outerIndex, String description, Executor executor, LocalDate doneDate, String doneIndex) {
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
    }

    @Override
    public LocalDate getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(LocalDate doneDate) {
        this.doneDate = doneDate;
    }

    @Override
    public String getDoneIndex() {
        return doneIndex;
    }

    public void setDoneIndex(String doneIndex) {
        this.doneIndex = doneIndex;
    }

    @Override
    public boolean getGenerics() {
        return true;
    }
}
