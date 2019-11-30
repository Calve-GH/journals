package com.github.calve.service.journal;

import com.github.calve.model.etc.Executor;
import com.github.calve.model.journal.Generic;
import com.github.calve.to.MailTo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface GenericService extends MailSaver {
    List<Generic> findMails();

    Page<Generic> findMails(Pageable pageable);

    Generic save(Generic mail);

    Generic save(MailTo mail, Map<String, Executor> cache);

    int delete(Integer id);

    Generic findById(Integer id);
}
