package com.github.calve.service;

import com.github.calve.model.Mail;
import com.github.calve.to.MailTo;

public interface MailSaver {
    Mail save(MailTo mail);
}
