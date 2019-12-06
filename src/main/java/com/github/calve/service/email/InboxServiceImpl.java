package com.github.calve.service.email;

import com.github.calve.model.email.Inbox;
import com.github.calve.model.etc.Contact;
import com.github.calve.repository.ContactRepository;
import com.github.calve.repository.InboxRepository;
import com.github.calve.service.generator.IndexGenerator;
import com.github.calve.to.etc.DataTable;
import com.github.calve.util.to.DataTablesInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.github.calve.service.utils.PagingUtils.constructPage;
import static com.github.calve.service.utils.PagingUtils.constructPageableSpecification;
import static com.github.calve.to.utils.EmailTransformUtil.packInboxList;

@Service
public class InboxServiceImpl implements InboxService {

    private InboxRepository repository;
    private ContactRepository contactRepository;
    private IndexGenerator indexGenerator;

    @Autowired
    public InboxServiceImpl(InboxRepository repository, ContactRepository contactRepository, @Qualifier("inbox") IndexGenerator indexGenerator) {
        this.repository = repository;
        this.contactRepository = contactRepository;
        this.indexGenerator = indexGenerator;
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
    public Inbox save(Inbox mail) {
        Contact contact = contactRepository.findByAlias(mail.getContact().getName());
        if (Objects.isNull(mail.getGenIndex())) {
            mail.setGenIndex(indexGenerator.getNextIndex());
        }
        mail.setContact(contact);
        return repository.save(mail);
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
        Pair<Pageable, Specification<?>> specPair = constructPageableSpecification(dti);
        Page<Inbox> pages = Objects.isNull(specPair.getSecond()) ? findMails(specPair.getFirst()) : findSearchable(specPair);
        return constructPage(dti, pages, packInboxList(pages.getContent()));
    }

    @SuppressWarnings("unchecked")
    private Page<Inbox> findSearchable(Pair<Pageable, Specification<?>> spec) {
        return repository.findAll((Specification<Inbox>) spec.getSecond(), spec.getFirst());
    }
}
