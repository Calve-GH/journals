package com.github.calve.service.common;

import com.github.calve.model.common.Incoming;
import com.github.calve.model.etc.Executor;
import com.github.calve.service.FilteredSortedData;
import com.github.calve.to.excel.BaseMailTo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IncomingService extends FilteredSortedData {
    List<Incoming> findMails();

    Page<Incoming> findMails(Pageable pageable);

    Incoming save(Incoming mail);

    Incoming save(BaseMailTo mail, Map<String, Executor> executors) throws SQLException;

    int delete(Integer id);

    Incoming findById(Integer id);

    Integer getMaxGenIndex();
}
