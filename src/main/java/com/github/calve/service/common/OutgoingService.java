package com.github.calve.service.common;

import com.github.calve.model.etc.Executor;
import com.github.calve.model.common.Outgoing;
import com.github.calve.service.FilteredSortedData;
import com.github.calve.to.BaseMailTo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface OutgoingService extends FilteredSortedData {
    List<Outgoing> findMails();

    Page<Outgoing> findMails(Pageable pageable);

    Outgoing save(BaseMailTo mail);

    Outgoing save(BaseMailTo mail, Map<String, Executor> executors) throws SQLException;

    int delete(Integer id);

    Outgoing findById(Integer id);
}
