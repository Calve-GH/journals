package com.github.calve.service;

import com.github.calve.model.Info;
import com.github.calve.model.Request;
import com.github.calve.to.MailTo;

import java.time.LocalDate;
import java.util.List;

public interface InfoService extends MailSaver {
    List<Info> findMails();

    List<Info> findMailsBetween(LocalDate from, LocalDate to);

    Info save(MailTo mail);

    int delete(Integer id);

    Info findById(Integer id);
}
