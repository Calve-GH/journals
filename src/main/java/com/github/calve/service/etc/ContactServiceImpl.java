package com.github.calve.service.etc;

import com.github.calve.model.etc.Contact;
import com.github.calve.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {

    private ContactRepository repository;

    @Autowired
    public ContactServiceImpl(ContactRepository repository) {
        this.repository = repository;
    }

    @Override
    public Contact findContactByAlias(String alias) {
        return repository.findByAlias(alias);
    }

    @Override
    public List<Contact> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<Contact> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Contact save(Contact contact) {
        return repository.save(contact);
    }

    @Override
    public int delete(Integer id) {
        return repository.delete(id);
    }

    @Override
    public Contact findById(Integer id) {
        Optional<Contact> byId = repository.findById(id);
        return byId.orElse(null);
    }
}
