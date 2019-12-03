package com.github.calve.service.journal;

import com.github.calve.model.etc.Executor;
import com.github.calve.model.journal.Request;
import com.github.calve.repository.RequestRepository;
import com.github.calve.service.etc.ExecutorService;
import com.github.calve.to.etc.DataTable;
import com.github.calve.to.excel.MailTo;
import com.github.calve.util.exception.NotFoundException;
import com.github.calve.util.to.DataTablesInput;
import com.github.calve.util.excel.TransformUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.github.calve.service.utils.PagingUtils.constructPage;
import static com.github.calve.service.utils.PagingUtils.constructPageableSpecification;
import static com.github.calve.to.journal.MailTransformUtil.packRequestList;

@Service
public class RequestServiceImpl implements RequestService {

    private RequestRepository repo;
    private ExecutorService service;

    @Autowired
    public RequestServiceImpl(RequestRepository repo, ExecutorService service) {
        this.repo = repo;
        this.service = service;
    }

    public List<Request> findMails() {
        return repo.findAll();
    }

    @Override
    public Request save(Request mail) {
        Executor executor = service.findExecutorByName(mail.getExecutor().getName()); //refactoring мб как то по другому.
        if (executor == null) throw new NotFoundException("Исполнителя не существует в БД");
        mail.setExecutor(executor);
        return repo.save(mail);
    }

    @Override
    public int delete(Integer id) {
        return repo.delete(id);
    }

    @Override
    public Request findById(Integer id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Request save(MailTo mail, Map<String, Executor> cache) {
        Executor executor = cache.get(TransformUtils.clearExecutorName(mail.getExecutor()));
        return repo.save(TransformUtils.getRequest(mail, executor));
    }

    @Override
    public Page<Request> findMails(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    public DataTable findFilteredAndSort(DataTablesInput dti) {
        Pair<Pageable, Specification<?>> specPair = constructPageableSpecification(dti);
        Page<Request> pages = Objects.isNull(specPair.getSecond()) ? findMails(specPair.getFirst()) : findSearchable(specPair);
        return constructPage(dti, pages, packRequestList(pages.getContent()));
    }

    @SuppressWarnings("unchecked")
    private Page<Request> findSearchable(Pair<Pageable, Specification<?>> spec) {
        return repo.findAll((Specification<Request>) spec.getSecond(), spec.getFirst());
    }
}
