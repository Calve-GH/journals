package com.github.calve.service;

import com.github.calve.model.Complaint;
import com.github.calve.model.Executor;
import com.github.calve.repository.ComplaintRepository;
import com.github.calve.to.MailTo;
import com.github.calve.util.DateTimeUtil;
import com.github.calve.web.TransformUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class ComplaintServiceImpl implements ComplaintService {
    private ComplaintRepository repo;
    private ExecutorService service;

    @Autowired
    public ComplaintServiceImpl(ComplaintRepository repo, ExecutorService service) {
        this.repo = repo;
        this.service = service;
    }

    public List<Complaint> findMails() {
        return repo.findAll();
    }

    @Override
    public List<Complaint> findMailsBetween(LocalDate from, LocalDate to) {
        return DateTimeUtil.validateDateRange(from, to) ? repo.getBetween(from, to) : repo.findAll();
    }

    @Override
    public Complaint save(MailTo mail) {
        Executor executor = service.findExecutorByName(mail.getExecutor());
        return repo.save(TransformUtils.getComplaint(mail, executor));
    }

    @Override
    public int delete(Integer id) {
        return repo.delete(id);
    }

    @Override
    public Complaint findById(Integer id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Complaint save(MailTo mail, Map<String, Executor> cache) {
        Executor executor = cache.get(TransformUtils.clearExecutorName(mail.getExecutor()));
        return repo.save(TransformUtils.getComplaint(mail, executor));
    }
}
