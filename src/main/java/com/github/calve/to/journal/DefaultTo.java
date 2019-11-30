package com.github.calve.to.journal;

import com.github.calve.model.journal.Type;
import com.github.calve.to.etc.Remain;
import lombok.Data;

import java.time.LocalDate;
import java.util.Objects;

import static com.github.calve.util.DateTimeUtil.*;

@Data
public class DefaultTo extends RootTo {

    protected LocalDate doneDate;
    protected String doneIndex;
    //calculated fields;
    protected boolean excess = false;
    protected Remain remain;

    public DefaultTo() {
    }

    public DefaultTo(Integer id, LocalDate incomeDate, String incomeIndex, String correspondent,
                     LocalDate outerDate, String outerIndex, String description, String executor,
                     LocalDate doneDate, String doneIndex, Type mailType) {
        super(id, incomeDate, incomeIndex, correspondent, outerDate, outerIndex, description, executor);
        this.doneDate = doneDate;
        this.doneIndex = doneIndex;
//todo refactoring; два раза кидаешь тут ссыль на дату но если реф сейчас -> нарушу старый функционал!!!
        if (Objects.nonNull(this.incomeDate) && Objects.isNull(this.doneDate)) {
            this.remain = initRemains(this.incomeDate, getLastDay(this.incomeDate, isGeneric(mailType)));
            this.excess = initExcess(this.incomeDate, getLastDay(this.incomeDate, isGeneric(mailType)));
        } else {
            this.remain = Remain.DONE_REMAIN;
        }
    }

    private static boolean isGeneric(Type type) {
        return type.equals(Type.GENERIC);
    }

    @Override
    public String toString() {
        return "DefaultTo{" +
                "doneDate=" + doneDate +
                ", doneIndex='" + doneIndex + '\'' +
                ", excess=" + excess +
                ", remain=" + remain.toString() +
                ", id=" + id +
                ", incomeDate=" + incomeDate +
                ", incomeIndex='" + incomeIndex + '\'' +
                ", correspondent='" + correspondent + '\'' +
                ", outerDate=" + outerDate +
                ", outerIndex='" + outerIndex + '\'' +
                ", description='" + description + '\'' +
                ", executor='" + executor + '\'' +
                '}';
    }
}
