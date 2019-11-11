package com.github.calve.service;

import com.github.calve.model.Executor;
import com.github.calve.model.Mail;
import com.github.calve.to.MailTo;

import java.sql.SQLException;
import java.util.Map;

public interface MailSaver extends FilteredSortedData {
    Mail save(MailTo mail, Map<String, Executor> cache) throws SQLException;
}
