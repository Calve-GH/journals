package com.github.calve.service.journal;

import com.github.calve.model.etc.Executor;
import com.github.calve.model.journal.Request;
import com.github.calve.to.excel.MailTo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface RequestService extends MailSaver {
    List<Request> findMails();

    Page<Request> findMails(Pageable pageable);

    Request save(Request mail);

    Request save(MailTo mail, Map<String, Executor> cache);

    int delete(Integer id);

    Request findById(Integer id);
}
