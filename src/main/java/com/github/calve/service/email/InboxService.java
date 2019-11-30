package com.github.calve.service.email;

import com.github.calve.model.email.Inbox;
import com.github.calve.service.FilteredSortedData;
import com.github.calve.to.email.EmailTo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InboxService extends FilteredSortedData {
    List<Inbox> findMails();

    Page<Inbox> findMails(Pageable pageable);

    Inbox save(EmailTo mail);

    int delete(Integer id);

    Inbox findById(Integer id);
}
