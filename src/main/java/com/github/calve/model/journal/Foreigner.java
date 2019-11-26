package com.github.calve.model.journal;

import com.github.calve.model.etc.Executor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity()
@Table(name = "foreigners")
public class Foreigner extends IncomingMail {
    @Column(name = "proceeding_number", nullable = false)
    private String proceedingNumber;
    @Column(name = "debtor", nullable = false)
    private String debtor;

    public Foreigner() {
    }

    public Foreigner(Integer id, LocalDate incomeDate, String incomeIndex, String corr, LocalDate outerDate,
                     String outerIndex, String description, Executor executor, String proceedingNumber, String debtor) {
        super.setId(id);
        super.setIncomeDate(incomeDate);
        super.setIncomeIndex(incomeIndex);
        super.setCorrespondent(corr);
        super.setOuterDate(outerDate);
        super.setOuterIndex(outerIndex);
        super.setDescription(description);
        super.setExecutor(executor);
        this.proceedingNumber = proceedingNumber;
        this.debtor = debtor;
    }

    @Override
    public String getProceedingNumber() {
        return proceedingNumber;
    }

    public void setProceedingNumber(String proceedingNumber) {
        this.proceedingNumber = proceedingNumber;
    }

    @Override
    public String getDebtor() {
        return debtor;
    }

    public void setDebtor(String debtor) {
        this.debtor = debtor;
    }
}
