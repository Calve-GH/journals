package com.github.calve.service;

import com.github.calve.model.Complaint;
import com.github.calve.model.Executor;
import com.github.calve.to.MailTo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ComplaintService extends MailSaver {
    List<Complaint> findMails();

    Complaint save(MailTo mail);

    Complaint save(MailTo mail, Map<String, Executor> cache);

    int delete(Integer id);

    Complaint findById(Integer id);
}
