package com.github.calve.service;

import com.github.calve.model.Executor;
import com.github.calve.model.Generic;
import com.github.calve.to.MailTo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface GenericService extends MailSaver {
    List<Generic> findMails();

    Page<Generic> findMails(Pageable pageable);

    Generic save(MailTo mail);

    Generic save(MailTo mail, Map<String, Executor> cache);

    int delete(Integer id);

    Generic findById(Integer id);
}
