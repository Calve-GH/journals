package com.github.calve.service.etc;

import com.github.calve.model.etc.Executor;
import com.github.calve.service.common.IncomingService;
import com.github.calve.service.common.OutgoingService;
import com.github.calve.service.journal.*;
import com.github.calve.to.common.IncomingTo;
import com.github.calve.to.excel.BaseMailTo;
import com.github.calve.to.excel.MailTo;
import com.github.calve.to.utils.CommonTransformUtil;
import com.github.calve.util.excel.Journals;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.StreamSupport;

import static com.github.calve.util.excel.MailCreatorUtil.*;

@Service
public class StorageServiceImpl implements StorageService {

    private RequestService requestService;
    private ComplaintService complaintService;
    private GenericService genericService;
    private InfoService infoService;
    private ForeignerService foreignerService;
    private ApplicationService applicationService;
    private ExecutorService executorService;
    private OutgoingService outgoingService;
    private IncomingService incomingService;

    private Map<String, Executor> executorsCache; //todo warning т.к. если мы хотим инициализировать
    //todo кеш при констракте обьекта получим NPE т.к. прокси на этом этапе еще не существует.
    // TODO: 21.10.2019 инит кеша при каждом заходе в парсинг

    @Autowired
    public StorageServiceImpl(RequestService requestService, ComplaintService complaintService,
                              GenericService genericService, InfoService infoService,
                              ForeignerService foreignerService, ApplicationService applicationService,
                              ExecutorService executorService, OutgoingService outgoingService, IncomingService incomingService) {
        this.requestService = requestService;
        this.complaintService = complaintService;
        this.genericService = genericService;
        this.infoService = infoService;
        this.foreignerService = foreignerService;
        this.applicationService = applicationService;
        this.executorService = executorService;
        this.outgoingService = outgoingService;
        this.incomingService = incomingService;
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

    private static void fromSheetToListBasic(List<BaseMailTo> saveList, Sheet sheet) {
        StreamSupport.stream(sheet.spliterator(), false).skip(1).forEach(row -> saveList.add(parseRowToBase(row)));
//        sheet.forEach(row -> saveList.add(parseRowToBase(row)));
    }

    private static void fromSheetToListIncoming(List<IncomingTo> saveList, Sheet sheet) {
        StreamSupport.stream(sheet.spliterator(), false).skip(1).forEach(row -> saveList.add(parseRowToIncome(row)));
//        sheet.forEach(row -> saveList.add(parseRowToBase(row)));
    }

    private static void fromSheetToList(List<MailTo> saveList, Sheet sheet, Journals type) {
        StreamSupport.stream(sheet.spliterator(), false).skip(1).forEach(row -> saveList.add(parseRowTo(type, row)));
//        sheet.forEach(row -> saveList.add(parseRowTo(type, row)));
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

    @Override
    public void storeOutgoing(MultipartFile file) throws SQLException {
        executorsCache = initCache();
        saveFileToDatabaseOut(file);
    }

    // REFACTORING: 04.12.2019 friendly with incoming mails;
    // FIXME: 04.12.2019 WRONG FUNCTIONAL;
    @Override
    public void storeIncoming(MultipartFile file) throws SQLException {
        saveFileToDatabaseIn(file);
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

    private void saveFileToDatabaseOut(MultipartFile file) throws SQLException {
        if (validateFile(file)) {
            List<BaseMailTo> saveList = new ArrayList<>();
            fillSaveListBasic(file, saveList);
            if (Objects.nonNull(outgoingService)) {
                for (BaseMailTo mail : saveList) {
                    outgoingService.save(mail, executorsCache);
                }
            }
        }
    }

    private void saveFileToDatabaseIn(MultipartFile file) throws SQLException {
        if (validateFile(file)) {
            List<IncomingTo> saveList = new ArrayList<>();
            fillSaveListIncoming(file, saveList);
            if (Objects.nonNull(incomingService)) {
                for (IncomingTo mail : saveList) {
                    incomingService.save(CommonTransformUtil.unpackIncoming(mail));
                }
            }
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

    private void fillSaveListBasic(MultipartFile file, List<BaseMailTo> saveList) {
        try {
            Workbook workbook = WorkbookFactory.create(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            fromSheetToListBasic(saveList, sheet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fillSaveListIncoming(MultipartFile file, List<IncomingTo> saveList) {
        try {
            Workbook workbook = WorkbookFactory.create(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            fromSheetToListIncoming(saveList, sheet);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
