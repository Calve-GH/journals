package com.github.calve.service.journal;

import com.github.calve.model.etc.Executor;
import com.github.calve.model.journal.Generic;
import com.github.calve.repository.GenericRepository;
import com.github.calve.service.etc.ExecutorService;
import com.github.calve.to.etc.DataTable;
import com.github.calve.to.MailTo;
import com.github.calve.util.exception.NotFoundException;
import com.github.calve.util.to.DataTablesInput;
import com.github.calve.web.TransformUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.github.calve.service.ServiceUtils.constructPage;
import static com.github.calve.service.ServiceUtils.constructPageableSpecification;
import static com.github.calve.to.journal.MailTransformUtil.packGenericList;

@Service
public class GenericServiceImpl implements GenericService {
    private GenericRepository repo;
    private ExecutorService service;

    @Autowired
    public GenericServiceImpl(GenericRepository repo, ExecutorService service) {
        this.repo = repo;
        this.service = service;
    }

    public List<Generic> findMails() {
        return repo.findAll();
    }

    @Override
    public Generic save(Generic mail) {
        Executor executor = service.findExecutorByName(mail.getExecutor().getName());
        if (executor == null) throw new NotFoundException("Исполнителя не существует в БД");
        mail.setExecutor(executor);
        return repo.save(mail);
    }

    @Override
    public int delete(Integer id) {
        return repo.delete(id);
    }

    @Override
    public Generic findById(Integer id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Generic save(MailTo mail, Map<String, Executor> cache) {
        Executor executor = cache.get(TransformUtils.clearExecutorName(mail.getExecutor()));
        return repo.save(TransformUtils.getGeneric(mail, executor));
    }

    @Override
    public Page<Generic> findMails(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    public DataTable findFilteredAndSort(DataTablesInput dti) {
        Pair<Pageable, Specification<?>> specPair = constructPageableSpecification(dti);
        Page<Generic> pages = Objects.isNull(specPair.getSecond()) ? findMails(specPair.getFirst()) : findSearchable(specPair);
        return constructPage(dti, pages, packGenericList(pages.getContent()));
    }

    @SuppressWarnings("unchecked")
    private Page<Generic> findSearchable(Pair<Pageable, Specification<?>> spec) {
        return repo.findAll((Specification<Generic>) spec.getSecond(), spec.getFirst());
    }
}
