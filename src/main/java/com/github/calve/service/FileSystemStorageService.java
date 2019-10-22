package com.github.calve.service;

import com.github.calve.model.Executor;
import com.github.calve.model.Mail;
import com.github.calve.to.MailTo;
import com.github.calve.util.Journals;
import com.github.calve.util.builders.ToBuilder;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    private ExecutorService executorService;

    private Map<String, Executor> executorsCache; //todo warning т.к. если мы хотим инициализировать
    //todo кеш при констракте обьекта получим NPE т.к. прокси на этом этапе еще не существует.
    // TODO: 21.10.2019 инит кеша при каждом заходе в парсинг

    @Autowired
    public FileSystemStorageService(RequestService requestService, ComplaintService complaintService,
                                    GenericService genericService, InfoService infoService,
                                    ForeignerService foreignerService, ApplicationService applicationService,
                                    ExecutorService executorService) {
        this.requestService = requestService;
        this.complaintService = complaintService;
        this.genericService = genericService;
        this.infoService = infoService;
        this.foreignerService = foreignerService;
        this.applicationService = applicationService;
        this.executorService = executorService;
    }

    @Override
    public void storeRequests(MultipartFile file) throws SQLException {
        saveFileToDatabase(file, Journals.REQUESTS);
    }

    @Override
    public void storeComplaints(MultipartFile file) throws SQLException {
        saveFileToDatabase(file, Journals.COMPLAINTS);
    }

    @Override
    public void storeGenerics(MultipartFile file) throws SQLException {
        saveFileToDatabase(file, Journals.GENERICS);
    }

    @Override
    public void storeInfo(MultipartFile file) throws SQLException {
        saveFileToDatabase(file, Journals.INFO);
    }

    @Override
    public void storeForeigners(MultipartFile file) throws SQLException {
        saveFileToDatabase(file, Journals.FOREIGNERS);
    }

    @Override
    public void storeApplications(MultipartFile file) throws SQLException {
        saveFileToDatabase(file, Journals.APPLICATIONS);
    }

    // TODO: 20.10.2019 single responsibility
    private static boolean validateFile(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        if (file.isEmpty()) {
            //empty body exception
            return false;
        }
        return !filename.contains("..");
    }

    private Map<String, Executor> initCache() {
        List<Executor> executorsList = executorService.findAll();
        HashMap<String, Executor> executorsMap = new HashMap<>();

        for (Executor executor : executorsList) {
            executorsMap.put(executor.getName().toLowerCase(), executor);
        }
        return executorsMap;
    }

        private void saveFileToDatabase(MultipartFile file, Journals type) throws SQLException {
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

    private void saveWithService(Journals type, List<MailTo> list) throws SQLException {
        executorsCache = initCache();
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
            for (MailTo mail : list) {
                service.save(mail, executorsCache);
            }
        }
    }

    private static String getStringValue(Iterator<Cell> cellIterator) {
        return getStringValueDefault(cellIterator, "");
    }

    private static String getStringValueRequired(Iterator<Cell> cellIterator) {
        return getStringValueDefault(cellIterator, "б/н");
    }

    private static String getStringValueDefault(Iterator<Cell> cellIterator, String defaultValue) {
        Cell next = null;
        if (cellIterator.hasNext()) {
            next = cellIterator.next();
        }
        if (Objects.nonNull(next)) {
            switch (next.getCellType()) {
                case STRING: {
                    return next.getRichStringCellValue().getString();
                }
                case NUMERIC: {
                    return String.format("%.0f", next.getNumericCellValue());
                }
            }
        }
        return defaultValue;
    }

    private static LocalDate getDateValue(Iterator<Cell> cellIterator) {
        return getDateValueDefault(cellIterator, null);
    }

    private static LocalDate getDateValueRequired(Iterator<Cell> cellIterator) {
        return getDateValueDefault(cellIterator, NOW);
    }

    private static LocalDate getDateValueDefault(Iterator<Cell> cellIterator, LocalDate defaultValue) {
        try {
            return cellIterator.hasNext() ? cellIterator.next().getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : defaultValue;
        } catch (Exception e) {
            //empty body exception
        }
        return defaultValue;
    }

    private static MailTo parseRowTo(Journals type, Row row) {
        ToBuilder builder = new ToBuilder();
        Iterator<Cell> cellIterator = row.cellIterator();
        constructBasicFields(builder, cellIterator);
        constructMiddleFields(type, builder, cellIterator);
        constructExecutorField(type, builder, cellIterator);
        constructTail(type, builder, cellIterator);
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
