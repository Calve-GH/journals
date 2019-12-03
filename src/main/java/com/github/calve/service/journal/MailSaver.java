package com.github.calve.service.journal;

import com.github.calve.model.etc.Executor;
import com.github.calve.model.Mail;
import com.github.calve.service.FilteredSortedData;
import com.github.calve.to.excel.MailTo;

import java.sql.SQLException;
import java.util.Map;

public interface MailSaver extends FilteredSortedData {
    Mail save(MailTo mail, Map<String, Executor> cache) throws SQLException;
}
