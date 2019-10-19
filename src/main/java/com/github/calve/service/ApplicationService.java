package com.github.calve.service;

import com.github.calve.model.Application;
import com.github.calve.model.Generic;
import com.github.calve.to.MailTo;

import java.time.LocalDate;
import java.util.List;

public interface ApplicationService extends MailSaver {
    List<Application> findMails();

    List<Application> findMailsBetween(LocalDate from, LocalDate to);

    Application save(MailTo mail);

    int delete(Integer id);

    Application findById(Integer id);
}
