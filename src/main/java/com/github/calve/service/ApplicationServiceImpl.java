package com.github.calve.service;

import com.github.calve.model.Application;
import com.github.calve.model.Executor;
import com.github.calve.repository.ApplicationRepository;
import com.github.calve.to.MailTo;
import com.github.calve.util.DateTimeUtil;
import com.github.calve.web.TransformUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    private ApplicationRepository repo;
    private ExecutorService service;

    @Autowired
    public ApplicationServiceImpl(ApplicationRepository repo, ExecutorService service) {
        this.repo = repo;
        this.service = service;
    }

    public List<Application> findMails() {
        return repo.findAll();
    }

    @Override
    public List<Application> findMailsBetween(LocalDate from, LocalDate to) {
        return DateTimeUtil.validateDateRange(from, to) ? repo.getBetween(from, to) : repo.findAll();
    }

    @Override
    public Application save(MailTo mail) {
        Executor executor = service.findExecutorByName(mail.getExecutor());
        return repo.save(TransformUtils.getApplication(mail, executor));
    }

    @Override
    public int delete(Integer id) {
        return repo.delete(id);
    }

    @Override
    public Application findById(Integer id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Application save(MailTo mail, Map<String, Executor> cache) throws SQLException {
        try {
            Executor executor = cache.get(TransformUtils.clearExecutorName(mail.getExecutor()));
            return repo.save(TransformUtils.getApplication(mail, executor));
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }
}
