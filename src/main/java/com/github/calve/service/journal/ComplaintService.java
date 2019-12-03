package com.github.calve.service.journal;

import com.github.calve.model.etc.Executor;
import com.github.calve.model.journal.Complaint;
import com.github.calve.to.excel.MailTo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ComplaintService extends MailSaver {
    List<Complaint> findMails();

    Page<Complaint> findMails(Pageable pageable);

    Complaint save(Complaint mail);

    Complaint save(MailTo mail, Map<String, Executor> cache);

    int delete(Integer id);

    Complaint findById(Integer id);
}
