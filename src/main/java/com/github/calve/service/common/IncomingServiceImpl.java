package com.github.calve.service.common;

import com.github.calve.model.common.Incoming;
import com.github.calve.model.etc.Executor;
import com.github.calve.repository.IncomingRepository;
import com.github.calve.service.etc.ExecutorService;
import com.github.calve.to.BaseMailTo;
import com.github.calve.to.etc.DataTable;
import com.github.calve.util.to.DataTablesInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.github.calve.service.ServiceUtils.constructPage;
import static com.github.calve.service.ServiceUtils.constructPageableSpecification;
import static com.github.calve.to.utils.CommonTransformUtil.packIncomingList;

@Service
public class IncomingServiceImpl implements IncomingService {

    private IncomingRepository repository;
    private ExecutorService executorService;

    @Autowired
    public IncomingServiceImpl(IncomingRepository repository, ExecutorService executorService) {
        this.repository = repository;
        this.executorService = executorService;
    }

    @Override
    public List<Incoming> findMails() {
        return repository.findAll();
    }

    @Override
    public Page<Incoming> findMails(Pageable pageable) {
        return repository.findAll(pageable);
    }

// TODO: 26.11.2019  //refactoring
    @Override
    public Incoming save(Incoming mail) {
        Executor executor = executorService.findExecutorByName(mail.getExecutor().getName());
        if (Objects.isNull(mail.getGenIndex())) {
            int index = getLastGenIndex();
            mail.setGenIndex(index);
        }
        mail.setExecutor(executor);
        return repository.save(mail);
    }

    private int getLastGenIndex() {
        try {
            return repository.maxByYear(LocalDate.now().getYear()) + 1;
        } catch (Exception e) {
            return 1;
        }
    }

// TODO: 26.11.2019  //refactoring
    @Override
    public Incoming save(BaseMailTo mail, Map<String, Executor> executors) throws SQLException {
        return null;
    }

    @Override
    public int delete(Integer id) {
        return repository.delete(id);
    }

    @Override
    public Incoming findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public DataTable findFilteredAndSort(DataTablesInput dti) {
        Pair<Pageable, Specification<?>> specPair = constructPageableSpecification(dti);
        Page<Incoming> pages = Objects.isNull(specPair.getSecond()) ? findMails(specPair.getFirst()) : findSearchable(specPair);
        return constructPage(dti, pages, packIncomingList(pages.getContent()));
    }

    @SuppressWarnings("unchecked")
    private Page<Incoming> findSearchable(Pair<Pageable, Specification<?>> spec) {
        return repository.findAll((Specification<Incoming>) spec.getSecond(), spec.getFirst());
    }
}
