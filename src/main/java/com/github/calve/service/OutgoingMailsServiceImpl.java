package com.github.calve.service;

import com.github.calve.model.Executor;
import com.github.calve.model.OutgoingMail;
import com.github.calve.repository.OutgoingMailsRepository;
import com.github.calve.to.BaseMailTo;
import com.github.calve.to.DataTable;
import com.github.calve.util.to.DataTablesInput;
import com.github.calve.web.TransformUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class OutgoingMailsServiceImpl implements OutgoingMailsService {

    private OutgoingMailsRepository outgoingMailsRepository;
    private ExecutorService executorService;

    @Autowired
    public OutgoingMailsServiceImpl(OutgoingMailsRepository outgoingMailsRepository, ExecutorService executorService) {
        this.outgoingMailsRepository = outgoingMailsRepository;
        this.executorService = executorService;
    }

    @Override
    public List<OutgoingMail> findMails() {
        return outgoingMailsRepository.findAll();
    }

    @Override
    public Page<OutgoingMail> findMails(Pageable pageable) {
        return outgoingMailsRepository.findAll(pageable);
    }

    @Override
    public int delete(Integer id) {
        return outgoingMailsRepository.delete(id);
    }

    @Override
    public OutgoingMail findById(Integer id) {
        return outgoingMailsRepository.findById(id).orElse(null);
    }

    public int count() { // TODO: 28.10.2019  
        return outgoingMailsRepository.countByYear(LocalDate.now().getYear()) + 1;
    }

    //refactoring add Spring gen;
    @Override
    public OutgoingMail save(BaseMailTo mail) { // TODO: 28.10.2019 а как же 2й TO
        Executor executor = executorService.findExecutorByName(mail.getExecutor());
        if (Objects.isNull(mail.getGenIndex())) {
            int index = outgoingMailsRepository.countByYear(LocalDate.now().getYear()) + 1;
            mail.setGenIndex(index);
        }
        return outgoingMailsRepository.save(TransformUtils.getOutgoing(mail, executor));
    }

    @Override
    public OutgoingMail save(BaseMailTo mail, Map<String, Executor> cache) throws SQLException {
        try {
            Executor executor = cache.get(TransformUtils.clearExecutorName(mail.getExecutor()));
            OutgoingMail outgoing = TransformUtils.getOutgoing(mail, executor);
            System.out.println(outgoing); //todo sout
            return outgoingMailsRepository.save(outgoing);
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    private Page<OutgoingMail> findSearchable(Pageable pageable, Specification<OutgoingMail> spec) {
        return outgoingMailsRepository.findAll(spec, pageable);
    }

    public DataTable findFilteredAndSort(DataTablesInput dti) {

        Pageable pageable = TransformUtils.getPageable(dti);
        Specification<OutgoingMail> spec = TransformUtils.getSpecification(dti);

        if (Objects.isNull(spec)) {
            return constructPage(dti, findMails(pageable));
        } else {
            return constructPage(dti, findSearchable(pageable, spec));
        }
    }

    //refactoring mb generics
    private static DataTable constructPage(DataTablesInput dti, Page<? extends OutgoingMail> mails) {
        return DataTable.builder()
                .data(getBaseTos(mails))
                .recordsTotal(mails.getTotalElements())
                .recordsFiltered(mails.getTotalElements())
                .draw(dti.getDraw())
                .start(dti.getStart())
                .build();
    }

    private static List<BaseMailTo> getBaseTos(Page<? extends OutgoingMail> mails) {
        return TransformUtils.getBaseToList(mails.getContent());
    }
}
