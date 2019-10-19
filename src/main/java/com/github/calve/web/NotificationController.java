package com.github.calve.web;

import com.github.calve.service.NotificationService;
import com.github.calve.to.MailNotificationTo;
import com.github.calve.to.NotificationTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = NotificationController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class NotificationController {
    static final String REST_URL = "/rest/notifications/";

    private NotificationService service;

    @Autowired
    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @GetMapping
    public String getMailNotifications() {
        MailNotificationTo.initMailNotification(service.getNotificationsMap());//boilerplate code
        return MailNotificationTo.getInstance().toString();
    }
    @GetMapping("to/")
    public NotificationTo getIndexNotifications() {
        MailNotificationTo.initMailNotification(service.getNotificationsMap());//boilerplate code
        return MailNotificationTo.getNotificationTo();
    }
}
