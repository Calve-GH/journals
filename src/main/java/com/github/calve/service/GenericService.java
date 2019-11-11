package com.github.calve.service;

import com.github.calve.model.Application;
import com.github.calve.model.Executor;
import com.github.calve.model.Generic;
import com.github.calve.to.MailTo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface GenericService extends MailSaver {
    List<Generic> findMails();

    Generic save(MailTo mail);

    Generic save(MailTo mail, Map<String, Executor> cache);

    int delete(Integer id);

    Generic findById(Integer id);
}
