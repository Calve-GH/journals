package com.github.calve.util.excel;

public enum Columns {
    ID("Дата поступления"),
    II("Индекс документа"),
    COR("Корреспондент"),
    OD("Реквизиты входящего. Дата"),
    OI("Реквизиты входящего. Номер"),
    WD("ИП по месту работы. Дата"),
    WI("ИП по месту работы. Номер"),
    AU("Орган выдавший документ"),
    DES("Краткое содеожание"),
    EX("Передан на исполнение"),
    DD("Отметка об исполнении. Дата"),
    DI("Отметка об исполнении. Номер"),
    DR("Результат исполнения"),
    REM("Дней осталось"),
    DEB("ФИО должника"),
    PC("Количество поступивших ИП"),
    SD("Дата отправки"),
    PN("Номер исполнительного производства или наряда"),
    IO("Порядковый регистрационный номер"),
    CORR("Корреспондент(получатель)"),
    CN("Наименование корреспонденции"),
    EFIO("ФИО исполнителя");

    private String name;

    Columns(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
