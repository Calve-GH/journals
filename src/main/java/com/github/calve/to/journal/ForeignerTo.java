package com.github.calve.to.journal;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ForeignerTo extends RootTo {
    private String proceedingNumber;
    private String debtor;

    public ForeignerTo() {
    }

    public ForeignerTo(Integer id, LocalDate incomeDate, String incomeIndex, String correspondent,
                       LocalDate outerDate, String outerIndex, String description, String executor,
                       String proceedingNumber, String debtor) {
        super(id, incomeDate, incomeIndex, correspondent, outerDate, outerIndex, description, executor);
        this.proceedingNumber = proceedingNumber;
        this.debtor = debtor;
    }
}
