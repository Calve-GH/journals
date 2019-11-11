package com.github.calve.service;

import com.github.calve.model.Executor;
import com.github.calve.model.Foreigner;
import com.github.calve.to.MailTo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ForeignerService extends MailSaver {
    List<Foreigner> findMails();

    Page<Foreigner> findMails(Pageable pageable);

    Foreigner save(MailTo mail);

    Foreigner save(MailTo mail, Map<String, Executor> cache);

    int delete(Integer id);

    Foreigner findById(Integer id);
}
