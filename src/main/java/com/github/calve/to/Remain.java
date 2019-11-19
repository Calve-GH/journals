package com.github.calve.to;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Remain implements Serializable {

    private final static DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd-MM-yyyy");
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
        return date.format(DTF);
    }

    public long getRemains() {
        return remains;
    }

    public String getRemainDate() {
        return remainDate;
    }
}
