package com.github.calve.util;

public enum Journals {

    REQUESTS("Запросы"),
    COMPLAINTS("Жалобы"),
    GENERICS("Проверка факт.прож."),
    INFO("Запросы ст.9"),
    FOREIGNERS(""),
    APPLICATIONS("Проверка бухгалтерии");

    private String name;

    Journals(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    }
