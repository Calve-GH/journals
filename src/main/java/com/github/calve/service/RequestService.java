package com.github.calve.service;

import com.github.calve.model.Executor;
import com.github.calve.model.Request;
import com.github.calve.to.MailTo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface RequestService extends MailSaver {
    List<Request> findMails();

    Page<Request> findMails(Pageable pageable);

    Request save(MailTo mail);

    Request save(MailTo mail, Map<String, Executor> cache);

    int delete(Integer id);

    Request findById(Integer id);
}
