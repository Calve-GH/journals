package com.github.calve.service;

import com.github.calve.model.OutgoingMail;

import java.time.LocalDate;
import java.util.List;

// TODO: 25.10.2019  
public class OutgoingMailsServiceImpl implements OutgoingMailsService {
    @Override
    public List<OutgoingMail> findMails() {
        return null;
    }

    @Override
    public List<OutgoingMail> findMailsBetween(LocalDate from, LocalDate to) {
        return null;
    }

    @Override
    public int delete(Integer id) {
        return 0;
    }

    @Override
    public OutgoingMail findById(Integer id) {
        return null;
    }
}
