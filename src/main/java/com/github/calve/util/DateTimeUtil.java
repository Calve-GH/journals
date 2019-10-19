package com.github.calve.util;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy"); //refactoring

    // DataBase doesn't support LocalDate.MIN/MAX
    private static final LocalDate MIN_DATE = LocalDate.of(1, 1, 1);
    private static final LocalDate MAX_DATE = LocalDate.of(3000, 1, 1);

    private DateTimeUtil() {
        throw new AssertionError("Error calling DataTimeUtil constructor.");
    }

    //hardcode
    public static boolean initExcess(LocalDate incomeDate) {
        LocalDate lastDay = getLastDay(incomeDate);
        LocalDate now = LocalDate.now();                               //boilerplate code
        if (lastDay.compareTo(now) >= 0) {
            return now.datesUntil(lastDay).count() <= 3;
        }
        return true;
    }

    public static long initRemains(LocalDate incomeDate) {
        if (Objects.isNull(incomeDate)) return 0;
        LocalDate lastDay = getLastDay(incomeDate);
        LocalDate now = LocalDate.now();                               //boilerplate code
        if (lastDay.compareTo(now) >= 0) {
            return now.datesUntil(lastDay).count();
        }
        return (lastDay.datesUntil(now).count()) * (-1L);
    }

    private static LocalDate getLastDay(LocalDate incomeDate) {
        return incomeDate.plusDays(incomeDate.getDayOfWeek().equals(DayOfWeek.SATURDAY) ? 15 : 14);
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
        if (Objects.isNull(from) || Objects.isNull(to)) {return false;}
        return !from.isAfter(to);
    }
}
