package com.github.calve.service;

import com.github.calve.model.Executor;
import com.github.calve.model.Info;
import com.github.calve.repository.InfoRepository;
import com.github.calve.to.DataTable;
import com.github.calve.to.MailTo;
import com.github.calve.util.to.DataTablesInput;
import com.github.calve.web.TransformUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.github.calve.service.ServiceUtils.constructPage;

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

    @Override
    public Info save(MailTo mail, Map<String, Executor> cache) {
        Executor executor = cache.get(TransformUtils.clearExecutorName(mail.getExecutor()));
        return repo.save(TransformUtils.getInfo(mail, executor));
    }

    @Override
    public DataTable findFilteredAndSort(DataTablesInput dti) {

        Pageable pageable = TransformUtils.getPageable(dti);
        Specification<Info> spec = TransformUtils.getSpecification(dti);

        if (Objects.isNull(spec)) {
            return constructPage(dti, findMails(pageable));
        } else {
            return constructPage(dti, findSearchable(pageable, spec));
        }
    }

    @Override
    public Page<Info> findMails(Pageable pageable) {
        return repo.findAll(pageable);
    }

    private Page<Info> findSearchable(Pageable pageable, Specification<Info> spec) {
        return repo.findAll(spec, pageable);
    }
}
