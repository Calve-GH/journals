package com.github.calve.service;

import com.github.calve.model.Executor;
import com.github.calve.model.Request;
import com.github.calve.to.MailTo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface RequestService extends MailSaver {
    List<Request> findMails();

    List<Request> findMailsBetween(LocalDate from, LocalDate to);

    Request save(MailTo mail);

    Request save(MailTo mail, Map<String, Executor> cache);

    int delete(Integer id);

    Request findById(Integer id);
}
