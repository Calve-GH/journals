package com.github.calve.service;

import com.github.calve.model.Executor;
import com.github.calve.model.Info;
import com.github.calve.to.MailTo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface InfoService extends MailSaver {
    List<Info> findMails();

    Page<Info> findMails(Pageable pageable);

    Info save(MailTo mail);

    Info save(MailTo mail, Map<String, Executor> cache);

    int delete(Integer id);

    Info findById(Integer id);
}
