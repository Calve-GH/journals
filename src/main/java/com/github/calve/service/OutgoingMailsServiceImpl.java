package com.github.calve.service;

import com.github.calve.model.Executor;
import com.github.calve.model.OutgoingMail;
import com.github.calve.repository.OutgoingMailsRepository;
import com.github.calve.to.BaseMailTo;
import com.github.calve.web.TransformUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
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
    public List<OutgoingMail> findMailsBetween(LocalDate from, LocalDate to) {
        return outgoingMailsRepository.getBetween(from, to);
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

    @Override
    public OutgoingMail save(BaseMailTo mail) { // TODO: 28.10.2019 а как же 2й TO
        Executor executor = executorService.findExecutorByName(mail.getExecutor());
        if (Objects.isNull(mail.getGenIndex())) {
            int index = outgoingMailsRepository.countByYear(LocalDate.now().getYear()) + 1;
            mail.setGenIndex(index);
        }
        return outgoingMailsRepository.save(TransformUtils.getOutgoing(mail, executor));
    }
}
