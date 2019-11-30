package com.github.calve.to.journal;

import com.github.calve.model.journal.Type;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ApplicationTo extends DefaultTo {
    private LocalDate workDate;
    private String workIndex;
    private String authority;
    private String proceedingNumber;

    public ApplicationTo() {
    }

    public ApplicationTo(Integer id, LocalDate incomeDate, String incomeIndex, String correspondent, LocalDate outerDate,
                         String outerIndex, String description, String executor, LocalDate doneDate, String doneIndex, Type mailType,
                         LocalDate workDate, String workIndex, String authority, String proceedingNumber) {
        super(id, incomeDate, incomeIndex, correspondent, outerDate, outerIndex, description, executor, doneDate, doneIndex, mailType);
        this.workDate = workDate;
        this.workIndex = workIndex;
        this.authority = authority;
        this.proceedingNumber = proceedingNumber;
    }
}
