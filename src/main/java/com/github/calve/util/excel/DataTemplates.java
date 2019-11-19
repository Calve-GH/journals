package com.github.calve.util.excel;

import com.github.calve.model.*;

import java.time.LocalDate;

public class DataTemplates {
    private static final LocalDate NOW = LocalDate.now();
    private static final Executor EXECUTOR = new Executor("Сапожников В.С.");
    public static final String EXAMPLE = "образец";
    public static final OutgoingMail OUTGOING_TEMPLATE = new OutgoingMail(0, NOW, "Номер исполнительного производства или наряда",
            1234567, "Корреспондент", "Наименование корреспонденции", EXECUTOR);
    public static final Request REQUEST_TEMPLATE = new Request(0, NOW, "1234567", "Корреспондент",
            NOW, "1234567", "Описанеи корреспонденции", EXECUTOR, NOW, "1234567");
    public static final Info INFO_TEMPLATE = new Info(0, NOW, "1234567", "Корреспондент", NOW,
            "1234567", "Описанеи корреспонденции", EXECUTOR, NOW, "1234567", "");
    public static final Application APPLICATION_TEMPLATE = new Application(0, NOW, "1234567",
            "Корреспондент", NOW, "1234567", "Описанеи корреспонденции", EXECUTOR, NOW, "1234567",
            NOW, "1234567", "", "");
    public static final Foreigner FOREIGNER_TEMPLATE = new Foreigner(0, NOW, "1234567",
            "Корреспондент", NOW, "1234567", "Описанеи корреспонденции", EXECUTOR, "1234567", "Должник");
}
