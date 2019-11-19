package com.github.calve.service;

import com.github.calve.model.Mail;
import com.github.calve.repository.*;
import com.github.calve.util.Journals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.github.calve.util.DateTimeUtil.*;
import static com.github.calve.util.Journals.*;

@Service
public class NotificationServiceImpl implements NotificationService {

    private RequestRepository requestRepository;
    private ComplaintRepository complaintRepository;
    private InfoRepository infoRepository;
    private ApplicationRepository genericRepository;
    private GenericRepository applicationRepository;


    @Autowired
    public NotificationServiceImpl(RequestRepository requestRepository, ComplaintRepository complaintRepository,
                                   InfoRepository infoRepository, ApplicationRepository genericRepository,
                                   GenericRepository applicationRepository) {
        this.requestRepository = requestRepository;
        this.complaintRepository = complaintRepository;
        this.infoRepository = infoRepository;
        this.genericRepository = genericRepository;
        this.applicationRepository = applicationRepository;
    }

    @Override
    public Map<Journals, Integer> getNotificationsMap() {
        Map<Journals, Integer> notificationValues = new LinkedHashMap<>();
        calcRequests(notificationValues);
        calcComplaints(notificationValues);
        calcGenerics(notificationValues);
        calcInfo(notificationValues);
        calcApplications(notificationValues);
        return notificationValues;
    }

    //refactoring enum map
    private void calcRequests(Map<Journals, Integer> map) {
        map.put(REQUESTS, findExpired(requestRepository.findAll(), false));
    }

    private void calcComplaints(Map<Journals, Integer> map) {
        map.put(COMPLAINTS, findExpired(complaintRepository.findAll(), false));
    }

    private void calcGenerics(Map<Journals, Integer> map) {
        map.put(GENERICS, findExpired(genericRepository.findAll(), true));
    }

    private void calcInfo(Map<Journals, Integer> map) {
        map.put(INFO, findExpired(infoRepository.findAll(), false));
    }

    private void calcApplications(Map<Journals, Integer> map) {
        map.put(APPLICATIONS, findExpired(applicationRepository.findAll(), false));
    }

    private int findExpired(List<? extends Mail> list, boolean generics) {
        int count = 0;
        for (Mail mail : list) {
            if (Objects.isNull(mail.getDoneDate()) && initExcess(mail.getIncomeDate(), getLastDay(mail.getIncomeDate(), generics))) {
                count++;
            }
        }

        return count;
    }
}
