package com.github.calve.service;

import com.github.calve.model.Executor;
import com.github.calve.model.OutgoingMail;
import com.github.calve.to.BaseMailTo;
import com.github.calve.to.DataTable;
import com.github.calve.to.DataTablesInput;
import com.github.calve.to.ex.Spec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface OutgoingMailsService {
    List<OutgoingMail> findMails();

    Page<OutgoingMail> findMails(Pageable pageable);

    List<OutgoingMail> findMailsBetween(LocalDate from, LocalDate to);

    OutgoingMail save(BaseMailTo mail);

    OutgoingMail save(BaseMailTo mail, Map<String, Executor> executors) throws SQLException;

//    OutgoingMail save(MailTo mail, Map<String, Executor> cache) throws SQLException;

    int delete(Integer id);

    OutgoingMail findById(Integer id);

    int count(); // TODO: 28.10.2019

    Page<OutgoingMail> findSearchable(Pageable pageable, Specification<OutgoingMail> spec);

    DataTable findSearchableMails(DataTablesInput dti);
}
