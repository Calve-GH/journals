package com.github.calve.util;

import com.github.calve.to.etc.Remain;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class DateTimeUtil {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy"); //refactoring

    // DataBase doesn't support LocalDate.MIN/MAX
    private static final LocalDate MIN_DATE = LocalDate.of(1, 1, 1);
    private static final LocalDate MAX_DATE = LocalDate.of(3000, 1, 1);

    private DateTimeUtil() {
        throw new AssertionError("Error calling DataTimeUtil constructor.");
    }

    //hardcode
    public static boolean initExcess(LocalDate incomeDate, LocalDate lastDay) {
//        LocalDate lastDay = getLastDay(incomeDate);
        LocalDate now = LocalDate.now();                               //boilerplate code
        if (lastDay.compareTo(now) >= 0) {
            return now.datesUntil(lastDay).count() <= 3;
        }
        return true;
    }

    public static Remain initRemains(LocalDate incomeDate, LocalDate lastDay) {
        if (Objects.isNull(incomeDate)) return Remain.DONE_REMAIN;
        //LocalDate lastDay = getLastDay(incomeDate);
        LocalDate now = LocalDate.now();                               //boilerplate code
        if (lastDay.compareTo(now) >= 0) {
            return new Remain(now.datesUntil(lastDay).count(), lastDay);
        }
        return new Remain((lastDay.datesUntil(now).count()) * (-1L), lastDay);
    }

    @Deprecated
    public static LocalDate getLastDay(LocalDate incomeDate, boolean generics) {
        return generics ? getLastValidDay15(incomeDate) :
                getLastValidDay10(incomeDate);
    }

    public static boolean initExcess(LocalDate incomeDate, int days, LocalDate doneDate) {
        if(Objects.nonNull(doneDate)) return false;
        LocalDate lastDay = getLastValidPeriodDay(incomeDate, days);
        LocalDate now = LocalDate.now();                               //boilerplate code
        if (lastDay.compareTo(now) >= 0) {
            return now.datesUntil(lastDay).count() <= 3;
        }
        return true;
    }

    public static Remain initRemains(LocalDate incomeDate, int days, LocalDate doneDate) {
        if (Objects.nonNull(doneDate) || Objects.isNull(incomeDate)) return Remain.DONE_REMAIN;
        LocalDate lastDay = getLastValidPeriodDay(incomeDate, days);
        LocalDate now = LocalDate.now();                               //boilerplate code
        if (lastDay.compareTo(now) >= 0) {
            return new Remain(now.datesUntil(lastDay).count(), lastDay);
        }
        return new Remain((lastDay.datesUntil(now).count()) * (-1L), lastDay);
    }

    private static LocalDate getLastValidDay10(LocalDate incomeDate) {
        return getLastValidPeriodDay(incomeDate, 15, 14);
    }

    private static LocalDate getLastValidDay15(LocalDate incomeDate) {
        return getLastValidPeriodDay(incomeDate, 20, 19);
    }

    private static LocalDate getLastValidPeriodDay(LocalDate incomeDate, int i, int i2) {
        return incomeDate.plusDays(incomeDate.getDayOfWeek().equals(DayOfWeek.SATURDAY) ? i : i2);
    }
    private static LocalDate getLastValidPeriodDay(LocalDate incomeDate, int days) {
        return incomeDate.plusDays(incomeDate.getDayOfWeek().equals(DayOfWeek.SATURDAY) ? ++days : days);
    }

    public static LocalDateTime adjustStartDateTime(LocalDate localDate) {
        return adjustDateTime(localDate, MIN_DATE, LocalTime.MIN);
    }

    public static LocalDateTime adjustEndDateTime(LocalDate localDate) {
        return adjustDateTime(localDate, MAX_DATE, LocalTime.MAX);
    }

    private static LocalDateTime adjustDateTime(LocalDate localDate, LocalDate defaultDate, LocalTime adjustTime) {
        return LocalDateTime.of(localDate != null ? localDate : defaultDate, adjustTime);
    }

    public static String toString(LocalDate ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static LocalDate parseLocalDate(@Nullable String str) {
        return StringUtils.isEmpty(str) ? null : LocalDate.parse(str);
    }

    public static LocalTime parseLocalTime(@Nullable String str) {
        return StringUtils.isEmpty(str) ? null : LocalTime.parse(str);
    }

    public static boolean validateDateRange(LocalDate from, LocalDate to) {
        if (Objects.isNull(from) || Objects.isNull(to)) {
            return false;
        }
        return !from.isAfter(to);
    }
}
