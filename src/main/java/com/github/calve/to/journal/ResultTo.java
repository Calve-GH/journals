package com.github.calve.to.journal;

import com.github.calve.model.journal.Type;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ResultTo extends DefaultTo {
    private String doneResult;

    public ResultTo() {
    }

    public ResultTo(Integer id, LocalDate incomeDate, String incomeIndex, String correspondent, LocalDate outerDate,
                    String outerIndex, String description, String executor, LocalDate doneDate, String doneIndex,
                    Type mailType, String doneResult) {
        super(id, incomeDate, incomeIndex, correspondent, outerDate, outerIndex, description, executor, doneDate, doneIndex, mailType);
        this.doneResult = doneResult;
    }
}
