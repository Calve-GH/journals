package com.github.calve.service.etc;

import com.github.calve.model.Mail;
import com.github.calve.model.common.Incoming;
import com.github.calve.repository.*;
import com.github.calve.util.excel.Journals;
import com.github.calve.util.excel.ExcelWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ExcelServiceImpl implements ExcelService {

    private RequestRepository requestRepository;
    private ComplaintRepository complaintRepository;
    private ApplicationRepository genericRepository;
    private InfoRepository infoRepository;
    private ForeignerRepository foreignerRepository;
    private GenericRepository applicationRepository;
    private OutgoingRepository outgoingMailsRepository;
    private IncomingRepository incomingRepository;

    @Autowired
    public ExcelServiceImpl(RequestRepository requestRepository, ComplaintRepository complaintRepository,
                            ApplicationRepository genericRepository, InfoRepository infoRepository,
                            ForeignerRepository foreignerRepository, GenericRepository applicationRepository,
                            OutgoingRepository outgoingMailsRepository, IncomingRepository incomingRepository) {
        this.requestRepository = requestRepository;
        this.complaintRepository = complaintRepository;
        this.genericRepository = genericRepository;
        this.infoRepository = infoRepository;
        this.foreignerRepository = foreignerRepository;
        this.applicationRepository = applicationRepository;
        this.outgoingMailsRepository = outgoingMailsRepository;
        this.incomingRepository = incomingRepository;
    }

    @Override
    public byte[] getExcelRepresentation() {
        List<List<? extends Mail>> list = new ArrayList<>();
        try {
            list.add(requestRepository.findAll());
            list.add(complaintRepository.findAll());
            list.add(genericRepository.findAll());
            list.add(infoRepository.findAll());
            list.add(foreignerRepository.findAll());
            list.add(applicationRepository.findAll());
            return ExcelWriter.getExcelFile(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //refactoring тут вопросы по логике зачем внурть сувать булеан и спрашивать о его значении
    public byte[] getOutgoingExcelRepresentation() {
        try {
            return ExcelWriter.getExcelOutFile(outgoingMailsRepository.findAll());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public byte[] getTemplate(Journals journals) {
        return ExcelWriter.getTemplate(journals);
    }

    @Override
    public byte[] getAllocation(LocalDate start, LocalDate end) {
        try {
            List<Incoming> list = incomingRepository.getAllocation(start, end);
            return ExcelWriter.getAllocationFile(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
