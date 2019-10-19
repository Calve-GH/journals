package com.github.calve.service;

import com.github.calve.model.Executor;
import com.github.calve.model.Foreigner;
import com.github.calve.model.Request;
import com.github.calve.repository.ForeignerRepository;
import com.github.calve.repository.RequestRepository;
import com.github.calve.to.MailTo;
import com.github.calve.util.DateTimeUtil;
import com.github.calve.web.TransformUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public class ForeignerServiceImpl implements ForeignerService {
    private ForeignerRepository repo;
    private ExecutorService service;

    @Autowired
    public ForeignerServiceImpl(ForeignerRepository repo, ExecutorService service) {
        this.repo = repo;
        this.service = service;
    }

    public List<Foreigner> findMails() {
        return repo.findAll();
    }

    @Override
    public List<Foreigner> findMailsBetween(LocalDate from, LocalDate to) {
        return DateTimeUtil.validateDateRange(from, to) ? repo.getBetween(from, to) : repo.findAll();
    }

    @Override
    public Foreigner save(MailTo mail) {
        Executor executor = service.findExecutorByName(mail.getExecutor());
        return repo.save(TransformUtils.getForeigner(mail, executor));
    }

    @Override
    public int delete(Integer id) {
        return repo.delete(id);
    }

    @Override
    public Foreigner findById(Integer id) {
        return repo.findById(id).orElse(null);
    }
}
