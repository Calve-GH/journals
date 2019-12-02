package com.github.calve.util.excel;

import com.github.calve.model.*;
import com.github.calve.model.common.Outgoing;
import com.github.calve.util.DateTimeUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.github.calve.util.excel.Columns.*;
import static com.github.calve.util.excel.DataTemplates.*;

public class ExcelWriter {
    private static final List<Columns> DEFAULT_COLUMNS = new ArrayList<>(Arrays.asList(ID, II, COR, OD, OI, DES, EX, DD, DI, REM));
    private static final List<Columns> RES_COLUMNS = new ArrayList<>(Arrays.asList(ID, II, COR, OD, OI, DES, EX, DD, DI, DR, REM));
    private static final List<Columns> FOREIGNERS_COLUMNS = new ArrayList<>(Arrays.asList(ID, II, COR, OD, OI, DEB, PC, EX));
    private static final List<Columns> APPLICATIONS_COLUMNS = new ArrayList<>(Arrays.asList(ID, II, COR, OD, OI, WD, WI, AU, PN, EX, DD, DI, REM));
    private static final List<Columns> OUTGOING_COLUMNS = new ArrayList<>(Arrays.asList(SD, PN, IO, CORR, CN, EFIO));

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static byte[] getExcelFile(List<List<? extends Mail>> tables) throws IOException {//refactoring name mb get byte array
        Workbook workbook = new XSSFWorkbook();
        int count = 0;
        for (List<? extends Mail> table : tables) {
            createSheet(workbook, table, TableNames.getNameByIndex(count++).getName());
        }
        return writeToFile(workbook);
    }

    //refactoring thinking mb memory leak
    private static String getSortedYear(Outgoing mail) {
        return Integer.toString(mail.getOuterDate().getYear());
    }

    // TODO: 12.11.2019 need new column writer; last upd 
    public static byte[] getExcelOutFile(List<Outgoing> outgoings) throws IOException {//refactoring name mb get byte array
        Workbook workbook = new XSSFWorkbook();

        Map<String, List<Outgoing>> mappedMails = outgoings.stream().collect(Collectors.groupingBy(ExcelWriter::getSortedYear));

        for (Map.Entry<String, List<Outgoing>> entry : mappedMails.entrySet()) {
            createOutcomeSheet(workbook, entry.getValue(), entry.getKey());
        }
        return writeToFile(workbook);
    }

    private static void createOutcomeSheet(Workbook workbook, List<Outgoing> table, String sheetName) throws IOException {
        Sheet sheet = workbook.createSheet(sheetName);
        setOutgoingTableColumnsWidth(sheet);
        int colNum = 0;
        int rowNum = 0;
        Row row1 = sheet.createRow(rowNum++);
        for (Columns column : OUTGOING_COLUMNS) {
            Cell headCell = row1.createCell(colNum++);
            headCell.setCellValue(column.getName());
            headCell.setCellStyle(getDefaultCellStyle(workbook));
        }
        for (Outgoing mail : table) { //refactoring sheet
            colNum = 0;
            Row row = sheet.createRow(rowNum++);
            row.createCell(colNum++).setCellValue(wrapDateCell(mail.getOuterDate()));
            row.createCell(colNum++).setCellValue(mail.getOuterIndex());
            row.createCell(colNum++).setCellValue(mail.getGenIndex());
            Cell corr = row.createCell(colNum++);
            corr.setCellValue(mail.getCorrespondent());
            corr.setCellStyle(getDefaultCellStyle(workbook));
            Cell descr = row.createCell(colNum++);
            descr.setCellValue(mail.getDescription());
            descr.setCellStyle(getDefaultCellStyle(workbook));
            row.createCell(colNum).setCellValue(mail.getExecutor().getName());
        }
    }

//refactoring 
// TODO: 20.11.2019 problem with type of mail identification; 
    private static void createSheet(Workbook workbook, List<? extends Mail> table, String sheetName) throws IOException {
        List<Columns> columns = DEFAULT_COLUMNS;

        if (table.stream().anyMatch(m -> Objects.nonNull(m.getWorkDate()))) {
            columns = APPLICATIONS_COLUMNS;
        } else if (table.stream().anyMatch(m -> Objects.nonNull(m.getDebtor()))) {
            columns = FOREIGNERS_COLUMNS;
        } else if (table.stream().anyMatch(m -> Objects.nonNull(m.getDoneResult()))) {
            columns = RES_COLUMNS;
        }
        Sheet sheet = workbook.createSheet(sheetName);

        int colNum = 0;
        int rowNum = 0;
        Row row1 = sheet.createRow(rowNum++);
        System.out.println(columns); //todo sout
        for (Columns column : columns) {
            row1.createCell(colNum++).setCellValue(column.getName());
            System.out.println(column.getName()); //todo sout
        }

        for (Mail mail : table) { //refactoring sheet

            colNum = 0;
            Row row = sheet.createRow(rowNum++);

            row.createCell(colNum++).setCellValue(wrapDateCell(mail.getIncomeDate()));
            row.createCell(colNum++).setCellValue(mail.getIncomeIndex());
            row.createCell(colNum++).setCellValue(mail.getCorrespondent());
            row.createCell(colNum++).setCellValue(wrapDateCell(mail.getOuterDate()));
            row.createCell(colNum++).setCellValue(mail.getOuterIndex());
            if (columns.contains(WD)) {
                row.createCell(colNum++).setCellValue(wrapDateCell(mail.getWorkDate()));
                row.createCell(colNum++).setCellValue(mail.getWorkIndex());
                row.createCell(colNum++).setCellValue(mail.getAuthority());
                row.createCell(colNum++).setCellValue(mail.getProceedingNumber());
            }
            if (columns.contains(DES)) {
                row.createCell(colNum++).setCellValue(mail.getDescription());
            }
            if (columns.contains(DEB)) {
                row.createCell(colNum++).setCellValue(mail.getDebtor());
                row.createCell(colNum++).setCellValue(mail.getProceedingNumber());
            }
            row.createCell(colNum++).setCellValue(mail.getExecutor().getName());
            if (columns.contains(DD)) {
                row.createCell(colNum++).setCellValue(wrapDateCell(mail.getDoneDate()));
            }
            if (columns.contains(DI)) {
                row.createCell(colNum++).setCellValue(mail.getDoneIndex());
            }
            if (columns.contains(DR)) {
                row.createCell(colNum++).setCellValue(mail.getDoneResult());
            }
            if (columns.contains(REM)) {
                if (Objects.nonNull(mail.getDoneDate())) {
                    row.createCell(colNum++).setCellValue(0);
                } else {
                    row.createCell(colNum++).setCellValue(DateTimeUtil.initRemains(mail.getIncomeDate(), DateTimeUtil.getLastDay(mail.getIncomeDate(), false)).getRemains()); // TODO: 18.11.2019 bug without generic mails;
                }
            }
        }
        //setArchiveTableColumnsWidth(sheet);
    }

    private static String wrapDateCell(LocalDate date) {
        if (Objects.isNull(date)) {
            return "";
        } else {
            return date.format(DATE_TIME_FORMATTER);
        }

    }

    private static void setOutgoingTableColumnsWidth(Sheet sheet) { // TODO: 16.10.2019
        sheet.setColumnWidth(0, 12 * 256);
        sheet.setColumnWidth(1, 12 * 256);
        sheet.setColumnWidth(2, 10 * 256);
        sheet.setColumnWidth(3, 36 * 256);
        sheet.setColumnWidth(4, 36 * 256);
        sheet.setColumnWidth(5, 8 * 256);

/*        sheet.setColumnWidth(0, 13 * 256);
        sheet.setColumnWidth(1, 12 * 256);
        sheet.setColumnWidth(2, 10 * 256);
        sheet.setColumnWidth(3, 36 * 256);
        sheet.setColumnWidth(4, 36 * 256);
        sheet.setColumnWidth(5, 8 * 256);
        sheet.setColumnWidth(6, 16 * 256);*/
    }

    private static byte[] writeToFile(Workbook workbook) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        return baos.toByteArray();
    }

    private static CellStyle defaultCellStyle = null;

    private static CellStyle getDefaultCellStyle(Workbook wb) {
        if (Objects.isNull(defaultCellStyle)) {
            defaultCellStyle = wb.createCellStyle();
            defaultCellStyle.setAlignment(HorizontalAlignment.GENERAL);
            defaultCellStyle.setVerticalAlignment(VerticalAlignment.TOP);
            defaultCellStyle.setWrapText(true);
        }
        return defaultCellStyle;
    }

    public static byte[] getTemplate(Journals journals) {
        Workbook workbook = new XSSFWorkbook();
        try {
            switch (journals) {
                case REQUESTS:
                case GENERICS: {
                    createSheet(workbook, Collections.singletonList(REQUEST_TEMPLATE), EXAMPLE);
                    break;
                }
                case COMPLAINTS:
                case INFO: {
                    createSheet(workbook, Collections.singletonList(INFO_TEMPLATE), EXAMPLE);
                    break;
                }
                case FOREIGNERS: {
                    createSheet(workbook, Collections.singletonList(FOREIGNER_TEMPLATE), EXAMPLE);
                    break;
                }
                case APPLICATIONS: {
                    createSheet(workbook, Collections.singletonList(APPLICATION_TEMPLATE), EXAMPLE);
                    break;
                }
                case OUTGOING: {
                    createOutcomeSheet(workbook, Collections.singletonList(OUTGOING_TEMPLATE), EXAMPLE);
                    break;
                }
//            default:{}
            }
        } catch (Exception e) {
            //empty body exception
        }

        try {
            return writeToFile(workbook);
        } catch (IOException e) {
            return null;
        }
    }
}
