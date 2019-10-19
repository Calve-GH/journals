package com.github.calve.util.excel;

import com.github.calve.model.Mail;
import com.github.calve.util.DateTimeUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.github.calve.util.excel.Columns.*;

public class ExcelWriter {
    private static final EnumSet<Columns> DEFAULT_COLUMNS = EnumSet.of(ID, II, COR, OD, OI, DES, EX, DD, DI, REM);
    private static final EnumSet<Columns> RES_COLUMNS = EnumSet.of(ID, II, COR, OD, OI, DES, EX, DD, DI, DR, REM);
    private static final EnumSet<Columns> FOREIGNERS_COLUMNS = EnumSet.of(ID, II, COR, OD, OI, EX, DEB, PC);
    private static final EnumSet<Columns> APPLICATIONS_COLUMNS = EnumSet.of(ID, II, COR, OD, OI, WD, WI, AU, EX, DD, DI, REM);

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static byte[] getExcelFile(List<List<? extends Mail>> tables) throws IOException {//refactoring name mb get byte array
        Workbook workbook = new XSSFWorkbook();
        int count = 0;
        for (List<? extends Mail> table : tables) {
            createSheet(workbook, table, TableNames.getNAmeByIndex(count++).getName());
        }
        return writeToFile(workbook);
    }

    private static void createSheet(Workbook workbook, List<? extends Mail> table, String sheetName) throws IOException {
        EnumSet<Columns> columns = DEFAULT_COLUMNS;
        if (table.stream().anyMatch(m -> Objects.nonNull(m.getDoneResult()))) {
            columns = RES_COLUMNS;
        } else if (table.stream().anyMatch(m -> Objects.nonNull(m.getDebtor()))) {
            columns = FOREIGNERS_COLUMNS;
        } else if (table.stream().anyMatch(m -> Objects.nonNull(m.getWorkDate()))) {
            columns = APPLICATIONS_COLUMNS;
        }

        Sheet sheet = workbook.createSheet(sheetName);

        int colNum = 0;
        int rowNum = 0;
        Row row1 = sheet.createRow(rowNum++);
        for (Columns column : columns) {
            row1.createCell(colNum++).setCellValue(column.getName());
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
            }
            if (columns.contains(WI)) {
                row.createCell(colNum++).setCellValue(mail.getWorkIndex());
            }
            if (columns.contains(AU)) {
                row.createCell(colNum++).setCellValue(mail.getAuthority());
            }
            if (columns.contains(DES)) {
                row.createCell(colNum++).setCellValue(mail.getDescription());
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
                    row.createCell(colNum++).setCellValue(DateTimeUtil.initRemains(mail.getIncomeDate()));
                }
            }
            if (columns.contains(DR)) {
                row.createCell(colNum++).setCellValue(mail.getDebtor());
            }
            if (columns.contains(DR)) {
                row.createCell(colNum++).setCellValue(mail.getProceedingNumber());
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

    private static void setArchiveTableColumnsWidth(Sheet sheet) { // TODO: 16.10.2019
        sheet.setColumnWidth(0, 13 * 256);
        sheet.setColumnWidth(1, 12 * 256);
        sheet.setColumnWidth(2, 10 * 256);
        sheet.setColumnWidth(3, 36 * 256);
        sheet.setColumnWidth(4, 36 * 256);
        sheet.setColumnWidth(5, 8 * 256);
        sheet.setColumnWidth(6, 16 * 256);
    }

    private static byte[] writeToFile(Workbook workbook) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        return baos.toByteArray();
    }
}
