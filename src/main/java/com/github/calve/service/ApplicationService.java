package com.github.calve.service;

import com.github.calve.model.Application;
import com.github.calve.model.Executor;
import com.github.calve.to.MailTo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ApplicationService extends MailSaver {
    List<Application> findMails();

    Page<Application> findMails(Pageable pageable);

    Application save(MailTo mail);

    Application save(MailTo mail, Map<String, Executor> cache) throws SQLException;

    int delete(Integer id);

    Application findById(Integer id);
}
