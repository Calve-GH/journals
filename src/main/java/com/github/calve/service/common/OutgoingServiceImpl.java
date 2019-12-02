package com.github.calve.service.common;

import com.github.calve.model.common.Outgoing;
import com.github.calve.model.etc.Executor;
import com.github.calve.repository.OutgoingRepository;
import com.github.calve.service.etc.ExecutorService;
import com.github.calve.to.BaseMailTo;
import com.github.calve.to.etc.DataTable;
import com.github.calve.util.to.DataTablesInput;
import com.github.calve.web.TransformUtils;
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
import static com.github.calve.to.utils.CommonTransformUtil.packOutgoingList;

@Service
public class OutgoingServiceImpl implements OutgoingService {

    private OutgoingRepository repository;
    private ExecutorService executorService;

    @Autowired
    public OutgoingServiceImpl(OutgoingRepository outgoingMailsRepository, ExecutorService executorService) {
        this.repository = outgoingMailsRepository;
        this.executorService = executorService;
    }

    @Override
    public List<Outgoing> findMails() {
        return repository.findAll();
    }

    @Override
    public Page<Outgoing> findMails(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public int delete(Integer id) {
        return repository.delete(id);
    }

    @Override
    public Outgoing findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    //refactoring add Spring gen;
    @Override
    public Outgoing save(Outgoing mail) { // TODO: 28.10.2019 а как же 2й TO
        Executor executor = executorService.findExecutorByName(mail.getExecutor().getName());
        if (Objects.isNull(mail.getGenIndex())) {
            int index = getLastGenIndex();
            mail.setGenIndex(index);
        }
        mail.setExecutor(executor);
        return repository.save(mail);
    }

    // TODO: 21.11.2019 Spring context listener, need as bean tha can be used in excel parser;
    private int getLastGenIndex() {
        return repository.maxByYear(LocalDate.now().getYear()) + 1;
    }

    @Override
    public Outgoing save(BaseMailTo mail, Map<String, Executor> cache) throws SQLException {
        try {
            Executor executor = cache.get(TransformUtils.clearExecutorName(mail.getExecutor()));
            Outgoing outgoing = TransformUtils.getOutgoing(mail, executor);
            System.out.println(outgoing); //todo sout
            return repository.save(outgoing);
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    private Page<Outgoing> findSearchable(Pageable pageable, Specification<Outgoing> spec) {
        return repository.findAll(spec, pageable);
    }

    public DataTable findFilteredAndSort(DataTablesInput dti) {
        Pair<Pageable, Specification<?>> specPair = constructPageableSpecification(dti);
        Page<Outgoing> pages = Objects.isNull(specPair.getSecond()) ? findMails(specPair.getFirst()) : findSearchable(specPair);
        return constructPage(dti, pages, packOutgoingList(pages.getContent()));
    }

    @SuppressWarnings("unchecked")
    private Page<Outgoing> findSearchable(Pair<Pageable, Specification<?>> spec) {
        return repository.findAll((Specification<Outgoing>) spec.getSecond(), spec.getFirst());
    }
}
