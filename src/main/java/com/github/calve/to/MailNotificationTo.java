package com.github.calve.to;

import com.github.calve.util.Journals;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;

public final class MailNotificationTo implements Serializable {

    private static final MailNotificationTo notificationTo = new MailNotificationTo();
    private Map<Journals, Integer> notifications = new LinkedHashMap<>(); //refactoring поплава с типами мапов разберись

    private MailNotificationTo() {
        if (notificationTo != null) {
            throw new AssertionError("Unit is already created!");
        }
    }

    private static void setNotifications(Map<Journals, Integer> map) {
        notificationTo.notifications = map;
    }

    private static Map<Journals, Integer> getNotifications() {
        return notificationTo.notifications;
    }

    public static MailNotificationTo getInstance() {
        return notificationTo;
    }

    public static void initMailNotification(Map<Journals, Integer> map) {
        setNotifications(map);
    }

    public static NotificationTo getNotificationTo() {
        return new NotificationTo(getNotifications());
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(";");
        for (Map.Entry<Journals, Integer> entry : getNotifications().entrySet()) {
            sj.add(entry.getKey().getName() + ": " + entry.getValue());
        }
        return sj.toString();
    }
}
