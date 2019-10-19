package com.github.calve.service;

import com.github.calve.model.Complaint;
import com.github.calve.to.MailTo;

import java.time.LocalDate;
import java.util.List;

public interface ComplaintService extends MailSaver {
    List<Complaint> findMails();

    List<Complaint> findMailsBetween(LocalDate from, LocalDate to);

    Complaint save(MailTo mail);

    int delete(Integer id);

    Complaint findById(Integer id);
}
