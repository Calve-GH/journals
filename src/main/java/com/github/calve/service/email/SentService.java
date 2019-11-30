package com.github.calve.service.email;

import com.github.calve.model.email.Sent;
import com.github.calve.service.FilteredSortedData;
import com.github.calve.to.email.EmailTo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SentService extends FilteredSortedData {
    List<Sent> findMails();

    Page<Sent> findMails(Pageable pageable);

    Sent save(EmailTo mail);

    int delete(Integer id);

    Sent findById(Integer id);
}
