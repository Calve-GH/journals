package com.github.calve.util.to;

import java.time.LocalDate;

public class DateCriteria {
    private String key;
    private LocalDate value;

    public DateCriteria() {
    }

    public DateCriteria(String key, LocalDate value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public LocalDate getValue() {
        return value;
    }

    public void setValue(LocalDate value) {
        this.value = value;
    }
}
