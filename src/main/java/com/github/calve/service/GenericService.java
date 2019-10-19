package com.github.calve.service;

import com.github.calve.model.Application;
import com.github.calve.model.Generic;
import com.github.calve.to.MailTo;

import java.time.LocalDate;
import java.util.List;

public interface GenericService extends MailSaver {
    List<Generic> findMails();

    List<Generic> findMailsBetween(LocalDate from, LocalDate to);

    Generic save(MailTo mail);

    int delete(Integer id);

    Generic findById(Integer id);
}
