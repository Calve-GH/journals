package com.github.calve.service;

import com.github.calve.to.MailTo;
import com.github.calve.util.Journals;
import com.github.calve.util.builders.ToBuilder;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Service
public class FileSystemStorageService implements StorageService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final LocalDate NOW = LocalDate.now();

    private RequestService requestService;
    private ComplaintService complaintService;
    private GenericService genericService;
    private InfoService infoService;
    private ForeignerService foreignerService;
    private ApplicationService applicationService;

    @Autowired
    public FileSystemStorageService(RequestService requestService, ComplaintService complaintService,
                                    GenericService genericService, InfoService infoService,
                                    ForeignerService foreignerService, ApplicationService applicationService) {
        this.requestService = requestService;
        this.complaintService = complaintService;
        this.genericService = genericService;
        this.infoService = infoService;
        this.foreignerService = foreignerService;
        this.applicationService = applicationService;
    }

    @Override
    public void storeRequests(MultipartFile file) {
        saveFileToDatabase(file, Journals.REQUESTS);
//        List<MailTo> listOnSave = new ArrayList<>();
//        validateFile(file);
//
//        try {
//            Workbook workbook = WorkbookFactory.create(file.getInputStream());
//            Sheet sheet = workbook.getSheetAt(0);
//            fromSheetToList(listOnSave, sheet, null);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        for (MailTo request : listOnSave) {
//            requestService.save(request);
//        }
    }
    @Override
    public void storeInfo(MultipartFile file) {
        saveFileToDatabase(file, Journals.INFO);
    }

    public void storeComplaints(MultipartFile file) {
        saveFileToDatabase(file, Journals.COMPLAINTS);
    }

    private static boolean validateFile(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        if (file.isEmpty()) {
            //empty body exception
            return false;
        }
        return !filename.contains("..");
    }

    private void saveFileToDatabase(MultipartFile file, Journals type) {
        if (validateFile(file)) {
            List<MailTo> saveList = new ArrayList<>();
            fillSaveList(file, type, saveList);
            saveWithService(type, saveList);
        }
    }

    private void fillSaveList(MultipartFile file, Journals type, List<MailTo> saveList) {
        try {
            Workbook workbook = WorkbookFactory.create(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            fromSheetToList(saveList, sheet, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void fromSheetToList(List<MailTo> saveList, Sheet sheet, Journals type) {
        sheet.forEach(row -> saveList.add(parseRowTo(type, row)));
    }


    private void saveWithService(Journals type, List<MailTo> list) {
        MailSaver service = null;
        switch (type) {
            case REQUESTS: {
                service = requestService;
                break;
            }
            case COMPLAINTS: {
                service = complaintService;
                break;
            }
            case GENERICS: {
                service = genericService;
                break;
            }
            case INFO: {
                service = infoService;
                break;
            }
            case FOREIGNERS: {
                service = foreignerService;
                break;
            }
            case APPLICATIONS: {
                service = applicationService;
                break;
            }
        }
        if (Objects.nonNull(service)) {
            list.forEach(service::save);
        }
    }

    // TODO: 20.10.2019 thinking
    private static String getStringValueFromNumber(Iterator<Cell> cellIterator) {
        try {
            return cellIterator.hasNext() ? Double.toString(cellIterator.next().getNumericCellValue()) : "";
        } catch (Exception e) {
            //empty body exception
            e.printStackTrace();
            return "";
        }
    }


    private static String getStringValue(Iterator<Cell> cellIterator) {
            Cell next = null;
        try {
            if (cellIterator.hasNext()) {
                next = cellIterator.next();
            }
            return Objects.nonNull(next) ? next.getRichStringCellValue().getString() : "";
        } catch (Exception e) {
            try {
                return String.format("%.0f", next.getNumericCellValue());
            } catch (Exception e1) {
                return "";
            }
        }
    }

    private static String getStringValueRequired(Iterator<Cell> cellIterator) {
        try {
            return cellIterator.hasNext() ? cellIterator.next().getRichStringCellValue().getString() : "б/н";
        } catch (Exception e) {
            //empty body exception
            return "б/н";
        }
    }

    private static LocalDate getDateValue(Iterator<Cell> cellIterator) {
        try {
            return cellIterator.hasNext() ? cellIterator.next().getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
        } catch (Exception e) {
            //empty body exception
            return null;
        }
    }

    private static LocalDate getDateValueRequired(Iterator<Cell> cellIterator) {

        try {
            return cellIterator.hasNext() ? cellIterator.next().getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : NOW;
        } catch (Exception e) {
            //empty body exception
            return NOW;
        }
    }
//boilerplate code

    private static MailTo parseRowTo(Journals type, Row row) {
        ToBuilder builder = new ToBuilder();
        Iterator<Cell> cellIterator = row.cellIterator();
        constructBasicFields(builder, cellIterator);
        constructMiddleFields(type, builder, cellIterator);
        constructExecutorField(type, builder, cellIterator);
        constructTail(type, builder, cellIterator);
        System.out.println(builder); //todo sout
        return builder.getMailTo();
    }

    private static void constructTail(Journals type, ToBuilder builder, Iterator<Cell> cellIterator) {
        if (!type.equals(Journals.FOREIGNERS)) {
            builder.setDoneDate(getDateValue(cellIterator))
                    .setDoneIndex(getStringValue(cellIterator));
            if (type.equals(Journals.COMPLAINTS) || type.equals(Journals.INFO)) {
                builder.setDoneResult(getStringValue(cellIterator));
            }
        }
    }

    private static void constructExecutorField(Journals type, ToBuilder builder, Iterator<Cell> cellIterator) {
        if (!type.equals(Journals.FOREIGNERS)) {
            builder.setExecutor(getStringValueRequired(cellIterator));
        } else {
            builder.setProceedingNumber(getStringValue(cellIterator))
                    .setExecutor(getStringValueRequired(cellIterator));
        }
    }

    private static void constructMiddleFields(Journals type, ToBuilder builder, Iterator<Cell> cellIterator) {
        if (type.equals(Journals.FOREIGNERS)) {
            builder.setDebtor(getStringValue(cellIterator));
        } else {
            if (!type.equals(Journals.APPLICATIONS)) {
                builder.setDescription(getStringValue(cellIterator));
            } else {
                builder.setWorkDate(getDateValue(cellIterator))
                        .setWorkIndex(getStringValue(cellIterator))
                        .setAuthority(getStringValue(cellIterator))
                        .setProceedingNumber(getStringValue(cellIterator));
            }
        }
    }

    private static void constructBasicFields(ToBuilder builder, Iterator<Cell> cellIterator) {
        builder.setIncomeDate(getDateValueRequired(cellIterator))
                .setIncomeIndex(getStringValueRequired(cellIterator))
                .setCorrespondent(getStringValueRequired(cellIterator))
                .setOuterDate(getDateValueRequired(cellIterator))
                .setOuterIndex(getStringValueRequired(cellIterator));
    }
}
