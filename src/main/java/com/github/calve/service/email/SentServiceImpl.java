package com.github.calve.service.email;

import com.github.calve.model.email.Sent;
import com.github.calve.model.etc.Contact;
import com.github.calve.repository.ContactRepository;
import com.github.calve.repository.SentRepository;
import com.github.calve.to.DataTable;
import com.github.calve.to.EmailTo;
import com.github.calve.util.to.DataTablesInput;
import com.github.calve.web.TransformUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SentServiceImpl implements SentService {

    private SentRepository repository;
    private ContactRepository contactRepository;

    @Autowired
    public SentServiceImpl(SentRepository repository, ContactRepository contactRepository) {
        this.repository = repository;
        this.contactRepository = contactRepository;
    }

    @Override
    public List<Sent> findMails() {
        return repository.findAll();
    }

    @Override
    public Page<Sent> findMails(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Sent save(EmailTo mail) {
        Contact contact = contactRepository.findByAlias(mail.getContact());
        return repository.save(TransformUtils.getSent(mail, contact));
    }

    @Override
    public int delete(Integer id) {
        return repository.delete(id);
    }

    @Override
    public Sent findById(Integer id) {
        Optional<Sent> byId = repository.findById(id);
        return byId.orElse(null);
    }

    // TODO: 26.11.2019
    //refactoring
    @Override
    public DataTable findFilteredAndSort(DataTablesInput dti) {
        return null;
    }
}
