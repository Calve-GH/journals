package com.github.calve.util.excel;

public enum TableNames {

    REQUEST("Заявления,ходатайства"),
    COMPLAINT("Жалобы на пост. действ. без действ."),
    GENERIC("Пост. о сов. отд. испол. действ"),
    INFO("Запросы ст.9 и иные инфор. хар."),
    FOREIGNER("Пост. исп. произ. из др ТОПИ"),
    APPLICATION("Заявление о провед провер.");

    private String name;

    TableNames(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static TableNames getNAmeByIndex(int index) {
        switch (index) {
            case 1 : return COMPLAINT;
            case 2 : return GENERIC;
            case 3 : return INFO;
            case 4 : return FOREIGNER;
            case 5 : return APPLICATION;
            default: return REQUEST;
        }
    }
}
