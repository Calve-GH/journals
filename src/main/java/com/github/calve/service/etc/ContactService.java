package com.github.calve.service.etc;

import com.github.calve.model.etc.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContactService {

    Contact findContactByAlias(String alias);

    List<Contact> findAll();

    Page<Contact> findAll(Pageable pageable);

    Contact save(Contact executor);

    int delete(Integer id);

    Contact findById(Integer id);
}
