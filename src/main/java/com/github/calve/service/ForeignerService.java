package com.github.calve.service;

import com.github.calve.model.Executor;
import com.github.calve.model.Foreigner;
import com.github.calve.model.Request;
import com.github.calve.to.MailTo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ForeignerService extends MailSaver {
    List<Foreigner> findMails();

    Foreigner save(MailTo mail);

    Foreigner save(MailTo mail, Map<String, Executor> cache);

    int delete(Integer id);

    Foreigner findById(Integer id);
}
