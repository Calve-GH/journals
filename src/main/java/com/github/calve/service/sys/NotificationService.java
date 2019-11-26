package com.github.calve.service.sys;

import com.github.calve.util.Journals;

import java.util.Map;

public interface NotificationService {
    Map<Journals, Integer> getNotificationsMap();
}
