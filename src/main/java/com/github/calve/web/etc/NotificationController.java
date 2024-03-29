package com.github.calve.web.etc;

import com.github.calve.service.sys.NotificationService;
import com.github.calve.to.etc.MailNotificationTo;
import com.github.calve.to.etc.NotificationTo;
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
        return MailNotificationTo.getNotificationTo().getTransferRepresentation();
    }
    @GetMapping("to/")
    public NotificationTo getIndexNotifications() {
        MailNotificationTo.initMailNotification(service.getNotificationsMap());//boilerplate code
        return MailNotificationTo.getNotificationTo();
    }
}
