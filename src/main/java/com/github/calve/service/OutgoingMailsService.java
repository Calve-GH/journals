package com.github.calve.service;

import com.github.calve.model.Executor;
import com.github.calve.model.OutgoingMail;
import com.github.calve.to.BaseMailTo;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface OutgoingMailsService {
    List<OutgoingMail> findMails();

    List<OutgoingMail> findMailsBetween(LocalDate from, LocalDate to);

    OutgoingMail save(BaseMailTo mail);

    OutgoingMail save(BaseMailTo mail, Map<String, Executor> executors) throws SQLException;

//    OutgoingMail save(MailTo mail, Map<String, Executor> cache) throws SQLException;

    int delete(Integer id);

    OutgoingMail findById(Integer id);

    int count(); // TODO: 28.10.2019
}
