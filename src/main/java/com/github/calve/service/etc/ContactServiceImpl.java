package com.github.calve.service.etc;

import com.github.calve.model.etc.Contact;
import com.github.calve.repository.ContactRepository;
import com.github.calve.to.etc.DataTable;
import com.github.calve.util.to.DataTablesInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.github.calve.service.utils.PagingUtils.constructPage;
import static com.github.calve.service.utils.PagingUtils.constructPageableNoDateSpecification;
import static com.github.calve.to.utils.ContactTransformUtil.packContactList;

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
    @Cacheable(value = "contacts")
    public List<Contact> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<Contact> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @CacheEvict(value = "contacts", allEntries = true)
    public Contact save(Contact contact) {
        return repository.save(contact);
    }

    @Override
    @CacheEvict(value = "contacts", allEntries = true)
    public int delete(Integer id) {
        return repository.delete(id);
    }

    @Override
    public Contact findById(Integer id) {
        Optional<Contact> byId = repository.findById(id);
        return byId.orElse(null);
    }

    // REFACTORING: 30.11.2019 add logic
    @Override
    public DataTable findFilteredAndSort(DataTablesInput dti) {
        Pair<Pageable, Specification<?>> specPair = constructPageableNoDateSpecification(dti);
        Page<Contact> pages = Objects.isNull(specPair.getSecond()) ? findAll(specPair.getFirst()) : findSearchable(specPair);
        return constructPage(dti, pages, packContactList(pages.getContent()));
    }

    @SuppressWarnings("unchecked")
    private Page<Contact> findSearchable(Pair<Pageable, Specification<?>> spec) {
        return repository.findAll((Specification<Contact>) spec.getSecond(), spec.getFirst());
    }

}
