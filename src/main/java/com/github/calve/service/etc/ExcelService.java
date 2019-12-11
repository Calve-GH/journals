package com.github.calve.service.etc;

import com.github.calve.util.excel.Journals;

import java.time.LocalDate;

public interface ExcelService {
    byte[] getExcelRepresentation();

    byte[] getOutgoingExcelRepresentation();

    byte[] getTemplate(Journals journals);

    byte[] getAllocation(LocalDate start, LocalDate end);
}
