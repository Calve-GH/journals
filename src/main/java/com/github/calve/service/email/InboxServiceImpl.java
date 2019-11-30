package com.github.calve.service.email;

import com.github.calve.model.email.Inbox;
import com.github.calve.model.etc.Contact;
import com.github.calve.repository.ContactRepository;
import com.github.calve.repository.InboxRepository;
import com.github.calve.to.etc.DataTable;
import com.github.calve.to.email.EmailTo;
import com.github.calve.util.to.DataTablesInput;
import com.github.calve.web.TransformUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class InboxServiceImpl implements InboxService {

    private InboxRepository repository;
    private ContactRepository contactRepository;

    @Autowired
    public InboxServiceImpl(InboxRepository repository, ContactRepository contactRepository) {
        this.repository = repository;
        this.contactRepository = contactRepository;
    }

    @Override
    public List<Inbox> findMails() {
        return repository.findAll();
    }

    @Override
    public Page<Inbox> findMails(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Inbox save(EmailTo mail) {
        Contact contact = contactRepository.findByAlias(mail.getContact());

        if (Objects.isNull(mail.getGenIndex())) {
            int index = getLastGenIndex();
            mail.setGenIndex(index);
        }

        return repository.save(TransformUtils.getInbox(mail, contact));
    }

    // TODO: 21.11.2019 Spring context listener, need as bean tha can be used in excel parser;
    private int getLastGenIndex() {
        try {
            return repository.maxByYear(LocalDate.now().getYear()) + 1;
        } catch (Exception e) {
            return 1;
        }
    }


    @Override
    public int delete(Integer id) {
        return repository.delete(id);
    }

    @Override
    public Inbox findById(Integer id) {
        Optional<Inbox> byId = repository.findById(id);
        return byId.orElse(null);
    }

    @Override
    public DataTable findFilteredAndSort(DataTablesInput dti) {
        return null;
    }
}
