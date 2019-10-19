package com.github.calve.service;

import com.github.calve.model.Foreigner;
import com.github.calve.model.Request;
import com.github.calve.to.MailTo;

import java.time.LocalDate;
import java.util.List;

public interface ForeignerService extends MailSaver {
    List<Foreigner> findMails();

    List<Foreigner> findMailsBetween(LocalDate from, LocalDate to);

    Foreigner save(MailTo mail);

    int delete(Integer id);

    Foreigner findById(Integer id);
}
