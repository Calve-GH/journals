package com.github.calve.service;

import com.github.calve.model.Mail;
import com.github.calve.repository.*;
import com.github.calve.util.excel.ExcelWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelServiceImpl implements ExcelService {

    private RequestRepository requestRepository;
    private ComplaintRepository complaintRepository;
    private ApplicationRepository genericRepository;
    private InfoRepository infoRepository;
    private ForeignerRepository foreignerRepository;
    private GenericRepository applicationRepository;

    @Autowired
    public ExcelServiceImpl(RequestRepository requestRepository, ComplaintRepository complaintRepository,
                            ApplicationRepository genericRepository, InfoRepository infoRepository,
                            ForeignerRepository foreignerRepository, GenericRepository applicationRepository) {
        this.requestRepository = requestRepository;
        this.complaintRepository = complaintRepository;
        this.genericRepository = genericRepository;
        this.infoRepository = infoRepository;
        this.foreignerRepository = foreignerRepository;
        this.applicationRepository = applicationRepository;
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
}
