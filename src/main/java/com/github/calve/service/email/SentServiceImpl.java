package com.github.calve.service.email;

import com.github.calve.model.email.Sent;
import com.github.calve.model.etc.Contact;
import com.github.calve.repository.ContactRepository;
import com.github.calve.repository.SentRepository;
import com.github.calve.to.email.SentInfo;
import com.github.calve.to.etc.DataTable;
import com.github.calve.util.to.DataTablesInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.github.calve.service.utils.PagingUtils.constructPage;
import static com.github.calve.service.utils.PagingUtils.constructPageableSpecification;
import static com.github.calve.to.utils.EmailTransformUtil.packSentList;

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
    public Sent save(Sent mail) {
        Contact contact = contactRepository.findByAlias(mail.getContact().getName());
        mail.setContact(contact);
        return repository.save(mail);
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
        Pair<Pageable, Specification<?>> specPair = constructPageableSpecification(dti);
        Page<Sent> pages = Objects.isNull(specPair.getSecond()) ? findMails(specPair.getFirst()) : findSearchable(specPair);
        return constructPage(dti, pages, packSentList(pages.getContent()));
    }

    @SuppressWarnings("unchecked")
    private Page<Sent> findSearchable(Pair<Pageable, Specification<?>> spec) {
        return repository.findAll((Specification<Sent>) spec.getSecond(), spec.getFirst());
    }

    @Override
    public void save(SentInfo info) {
        Contact contact = contactRepository.findByEmail(info.getEmail());
        if (Objects.nonNull(contact)) {
            // REFACTORING: 08.12.2019 do something;
            repository.save(new Sent(null, LocalDate.now(), contact, info.getDescription(), true));
        }
    }
}
