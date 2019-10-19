package com.github.calve.service;

import com.github.calve.model.Request;
import com.github.calve.to.MailTo;

import java.time.LocalDate;
import java.util.List;

public interface RequestService extends MailSaver {
    List<Request> findMails();

    List<Request> findMailsBetween(LocalDate from, LocalDate to);

    Request save(MailTo mail);

    int delete(Integer id);

    Request findById(Integer id);
}
