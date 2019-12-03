package com.github.calve.to.etc;

import java.io.Serializable;
import java.time.LocalDate;

import static com.github.calve.util.DateTimeUtil.DATE_TIME_FORMATTER;

public class Remain implements Serializable {

    public final static Remain DONE_REMAIN = new Remain(0, "");

    private final long remains;
    private final String remainDate;

    public Remain(long remains, String remainDate) {
        this.remains = remains;
        this.remainDate = remainDate;
    }

    public Remain(long remains, LocalDate remainDate) {
        this(remains, formatDate(remainDate));
    }

    private static String formatDate(LocalDate date) {
        return date.format(DATE_TIME_FORMATTER);
    }

    public long getRemains() {
        return remains;
    }

    public String getRemainDate() {
        return remainDate;
    }

    @Override
    public String toString() {
        return "Remain{" +
                "remains=" + remains +
                ", remainDate='" + remainDate + '\'' +
                '}';
    }
}
