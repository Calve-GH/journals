package com.github.calve.service;

import com.github.calve.model.Executor;
import com.github.calve.model.Info;
import com.github.calve.model.Request;
import com.github.calve.repository.InfoRepository;
import com.github.calve.repository.RequestRepository;
import com.github.calve.to.MailTo;
import com.github.calve.util.DateTimeUtil;
import com.github.calve.web.TransformUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public class InfoServiceImpl implements InfoService {
    private InfoRepository repo;
    private ExecutorService service;

    @Autowired
    public InfoServiceImpl(InfoRepository repo, ExecutorService service) {
        this.repo = repo;
        this.service = service;
    }

    public List<Info> findMails() {
        return repo.findAll();
    }

    @Override
    public List<Info> findMailsBetween(LocalDate from, LocalDate to) {
        return DateTimeUtil.validateDateRange(from, to) ? repo.getBetween(from, to) : repo.findAll();
    }

    @Override
    public Info save(MailTo mail) {
        Executor executor = service.findExecutorByName(mail.getExecutor());
        return repo.save(TransformUtils.getInfo(mail, executor));
    }

    @Override
    public int delete(Integer id) {
        return repo.delete(id);
    }

    @Override
    public Info findById(Integer id) {
        return repo.findById(id).orElse(null);
    }
}
