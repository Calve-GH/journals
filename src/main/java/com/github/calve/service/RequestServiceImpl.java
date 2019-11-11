package com.github.calve.service;

import com.github.calve.model.Executor;
import com.github.calve.model.Request;
import com.github.calve.repository.RequestRepository;
import com.github.calve.to.MailTo;
import com.github.calve.util.DateTimeUtil;
import com.github.calve.util.exception.NotFoundException;
import com.github.calve.web.TransformUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
    public Request save(MailTo mail) {
        Executor executor = service.findExecutorByName(mail.getExecutor()); //refactoring мб как то по другому.
        if (executor == null) throw new NotFoundException("Исполнителя не существует в БД");
        System.out.println(mail); //todo sout
        if (mail.getIncomeIndex() == null) System.out.println("II is null"); //todo sout
        else System.out.println("II isn't null"); //todo sout
        return repo.save(TransformUtils.getRequest(mail, executor));
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
    public Page<Request> findAll(Pageable pageable) {
        return repo.findAll(pageable);
    }
}
