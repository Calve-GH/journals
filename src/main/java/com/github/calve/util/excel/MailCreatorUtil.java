package com.github.calve.util.excel;

import com.github.calve.to.common.IncomingTo;
import com.github.calve.to.excel.BaseMailTo;
import com.github.calve.to.excel.MailTo;
import com.github.calve.util.builders.ToBuilder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.Iterator;

import static com.github.calve.util.excel.ParserUtil.*;

public final class MailCreatorUtil {

    private MailCreatorUtil() {
        throw new AssertionError("Cannot create instance of util class");
    }

    public static BaseMailTo parseRowToBase(Row row) {
        ToBuilder builder = new ToBuilder();
        Iterator<Cell> cellIterator = row.cellIterator();
        builder.setOuterDate(getDateValueRequired(cellIterator))
                .setOuterIndex(getStringValueRequired(cellIterator))
                .setGenIndex(getNumericValueDefault(cellIterator, -1))
                .setCorrespondent(getStringValueRequired(cellIterator))
                .setDescription(getStringValue(cellIterator))
                .setExecutor(getStringValueRequired(cellIterator));
        return builder.getBaseMailTo();
    }

    public static IncomingTo parseRowToIncome(Row row) {
        Iterator<Cell> cellIterator = row.cellIterator();
        IncomingTo dto = new IncomingTo();
        dto.setRegDate(getDateValueRequired(cellIterator));
        dto.setGenIndex(getNumericValueDefault(cellIterator, -1));
        dto.setDebtor(getStringValueRequired(cellIterator));
        dto.setDescription(getStringValue(cellIterator));
        dto.setExecutor(getStringValueRequired(cellIterator));
        return dto;
    }

    public static MailTo parseRowTo(Journals type, Row row) {
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
