package com.github.calve.model.journal;

import com.github.calve.model.etc.Executor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.StringJoiner;

@Entity
@Table(name = "requests")
public class Request extends IncomingMail {
    @Column(name = "done_date", nullable = false)
    private LocalDate doneDate;
    @Column(name = "done_index", nullable = false)
    private String doneIndex;

    public Request() {
    }

    public Request(Integer id, LocalDate incomeDate, String incomeIndex, String corr, LocalDate outerDate,
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

    @Override
    public String toString() {
        return "Request{" +
                "im=" + super.toString() +
                "doneDate=" + doneDate +
                ", doneIndex='" + doneIndex + '\'' +
                '}';
    }
}
