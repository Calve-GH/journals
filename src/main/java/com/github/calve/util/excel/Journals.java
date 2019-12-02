package com.github.calve.util.excel;

public enum Journals {

    REQUESTS("Запросы"),
    COMPLAINTS("Жалобы"),
    GENERICS("Проверка факт.прож."),
    INFO("Запросы ст.9"),
    FOREIGNERS(""),
    APPLICATIONS("Проверка бухгалтерии"),
    OUTGOING("Входящие");

    private String name;

    Journals(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
