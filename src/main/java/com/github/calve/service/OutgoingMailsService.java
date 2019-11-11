package com.github.calve.service;

import com.github.calve.model.Executor;
import com.github.calve.model.OutgoingMail;
import com.github.calve.to.BaseMailTo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface OutgoingMailsService extends FilteredSortedData {
    List<OutgoingMail> findMails();

    Page<OutgoingMail> findMails(Pageable pageable);

    OutgoingMail save(BaseMailTo mail);

    OutgoingMail save(BaseMailTo mail, Map<String, Executor> executors) throws SQLException;

    int delete(Integer id);

    OutgoingMail findById(Integer id);

    int count(); // TODO: 28.10.2019


}
