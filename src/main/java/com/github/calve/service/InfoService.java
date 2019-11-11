package com.github.calve.service;

import com.github.calve.model.Executor;
import com.github.calve.model.Info;
import com.github.calve.model.Request;
import com.github.calve.to.MailTo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface InfoService extends MailSaver {
    List<Info> findMails();

    Info save(MailTo mail);

    Info save(MailTo mail, Map<String, Executor> cache);

    int delete(Integer id);

    Info findById(Integer id);
}
