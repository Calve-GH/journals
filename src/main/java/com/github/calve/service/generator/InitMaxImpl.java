package com.github.calve.service.generator;

import com.github.calve.repository.InboxRepository;
import com.github.calve.repository.IncomingRepository;
import com.github.calve.repository.OutgoingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class InitMaxImpl implements InitMax {

    private IncomingRepository incomingRepository;
    private OutgoingRepository outgoingRepository;
    private InboxRepository inboxRepository;

    @Autowired
    public InitMaxImpl(IncomingRepository incomingRepository, OutgoingRepository outgoingRepository, InboxRepository inboxRepository) {
        this.incomingRepository = incomingRepository;
        this.outgoingRepository = outgoingRepository;
        this.inboxRepository = inboxRepository;
    }

    @Override
    public Integer getMaxIncomingGI() {
        try {
            return incomingRepository.maxByYear(LocalDate.now().getYear());
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public Integer getMaxOutgoingGI() {
        try {
            return outgoingRepository.maxByYear(LocalDate.now().getYear());
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public Integer getMaxInboxGI() {
        try {
            return inboxRepository.maxByYear(LocalDate.now().getYear());
        } catch (Exception e) {
            return 0;
        }
    }
}
