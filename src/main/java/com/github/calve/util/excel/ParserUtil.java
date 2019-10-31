package com.github.calve.util.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Iterator;
import java.util.Objects;

public final class ParserUtil {

    private static final LocalDate NOW = LocalDate.now();

    private ParserUtil() {
        throw new AssertionError("Cannot create instance of util class");
    }

    public static String getStringValue(Iterator<Cell> cellIterator) {
        return getStringValueDefault(cellIterator, "");
    }

    public static String getStringValueRequired(Iterator<Cell> cellIterator) {
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

    public static Integer getNumericValueDefault(Iterator<Cell> cellIterator, Integer defaultValue) {
        Cell next = null;
        if (cellIterator.hasNext()) {
            next = cellIterator.next();
        }
        if (Objects.nonNull(next)) {
            if (next.getCellType().equals(CellType.NUMERIC)) {
                return Double.valueOf(next.getNumericCellValue()).intValue(); //refactoring boxing-unboxing
            }
        }
        return defaultValue;
    }

    public static LocalDate getDateValue(Iterator<Cell> cellIterator) {
        return getDateValueDefault(cellIterator, null);
    }

    public static LocalDate getDateValueRequired(Iterator<Cell> cellIterator) {
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

}
