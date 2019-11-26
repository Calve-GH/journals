package com.github.calve.model.journal;

import com.github.calve.model.etc.Executor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity()
@Table(name = "applications")
public class Application extends IncomingMail {
    @Column(name = "work_date", nullable = false)
    private LocalDate workDate;
    @Column(name = "work_index", nullable = false)
    private String workIndex;
    @Column(name = "authority", nullable = false)
    private String authority;
    @Column(name = "proceeding_number", nullable = false)
    private String proceedingNumber;
    @Column(name = "done_date", nullable = false)
    private LocalDate doneDate;
    @Column(name = "done_index", nullable = false)
    private String doneIndex;

    public Application() {
    }

    public Application(Integer id, LocalDate incomeDate, String incomeIndex, String corr, LocalDate outerDate,
                       String outerIndex, String description, Executor executor, LocalDate doneDate, String doneIndex,
                       LocalDate workDate, String workIndex, String authority, String proceedingNumber) {
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
        this.workDate = workDate;
        this.workIndex = workIndex;
        this.authority = authority;
        this.proceedingNumber = proceedingNumber;
    }

    @Override
    public LocalDate getWorkDate() {
        return workDate;
    }

    public void setWorkDate(LocalDate workDate) {
        this.workDate = workDate;
    }

    @Override
    public String getWorkIndex() {
        return workIndex;
    }

    public void setWorkIndex(String workIndex) {
        this.workIndex = workIndex;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getProceedingNumber() {
        return proceedingNumber;
    }

    public void setProceedingNumber(String proceedingNumber) {
        this.proceedingNumber = proceedingNumber;
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
}
