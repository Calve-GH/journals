package com.github.calve.service;

import com.github.calve.model.OutgoingMail;

import java.time.LocalDate;
import java.util.List;

public interface OutgoingMailsService {
    List<OutgoingMail> findMails();

    List<OutgoingMail> findMailsBetween(LocalDate from, LocalDate to);

//    OutgoingMail save(MailTo mail);
//
//    OutgoingMail save(MailTo mail, Map<String, Executor> cache) throws SQLException;

    int delete(Integer id);

    OutgoingMail findById(Integer id);
}
