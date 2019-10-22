package com.github.calve.service;

import com.github.calve.model.Application;
import com.github.calve.model.Executor;
import com.github.calve.model.Generic;
import com.github.calve.model.Mail;
import com.github.calve.to.MailTo;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ApplicationService extends MailSaver {
    List<Application> findMails();

    List<Application> findMailsBetween(LocalDate from, LocalDate to);

    Application save(MailTo mail);

    Application save(MailTo mail, Map<String, Executor> cache) throws SQLException;

    int delete(Integer id);

    Application findById(Integer id);
}
