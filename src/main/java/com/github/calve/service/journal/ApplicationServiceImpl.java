package com.github.calve.service.journal;

import com.github.calve.model.journal.Application;
import com.github.calve.model.etc.Executor;
import com.github.calve.repository.ApplicationRepository;
import com.github.calve.service.etc.ExecutorService;
import com.github.calve.to.DataTable;
import com.github.calve.to.MailTo;
import com.github.calve.util.to.DataTablesInput;
import com.github.calve.web.TransformUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.github.calve.service.ServiceUtils.constructPage;

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

    @Override
    public DataTable findFilteredAndSort(DataTablesInput dti) {

        Pageable pageable = TransformUtils.getPageable(dti);
        Specification<Application> spec = TransformUtils.getSpecification(dti);

        if (Objects.isNull(spec)) {
            return constructPage(dti, findMails(pageable));
        } else {
            return constructPage(dti, findSearchable(pageable, spec));
        }
    }

    @Override
    public Page<Application> findMails(Pageable pageable) {
        return repo.findAll(pageable);
    }

    private Page<Application> findSearchable(Pageable pageable, Specification<Application> spec) {
        return repo.findAll(spec, pageable);
    }
}
