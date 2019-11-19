package com.github.calve.service;

import com.github.calve.util.Journals;

public interface ExcelService {
    byte[] getExcelRepresentation();

    byte[] getOutgoingExcelRepresentation();

    byte[] getTemplate(Journals journals);

}
