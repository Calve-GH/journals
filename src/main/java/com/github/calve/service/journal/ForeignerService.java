package com.github.calve.service.journal;

import com.github.calve.model.etc.Executor;
import com.github.calve.model.journal.Foreigner;
import com.github.calve.to.excel.MailTo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ForeignerService extends MailSaver {
    List<Foreigner> findMails();

    Page<Foreigner> findMails(Pageable pageable);

    Foreigner save(Foreigner mail);

    Foreigner save(MailTo mail, Map<String, Executor> cache);

    int delete(Integer id);

    Foreigner findById(Integer id);
}
