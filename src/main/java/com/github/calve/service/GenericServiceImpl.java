package com.github.calve.service;

import com.github.calve.model.Executor;
import com.github.calve.model.Generic;
import com.github.calve.repository.GenericRepository;
import com.github.calve.to.MailTo;
import com.github.calve.util.DateTimeUtil;
import com.github.calve.web.TransformUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
    public List<Generic> findMailsBetween(LocalDate from, LocalDate to) {
        return DateTimeUtil.validateDateRange(from, to) ? repo.getBetween(from, to) : repo.findAll();
    }

    @Override
    public Generic save(MailTo mail) {
        Executor executor = service.findExecutorByName(mail.getExecutor());
        return repo.save(TransformUtils.getGeneric(mail, executor));
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
}
