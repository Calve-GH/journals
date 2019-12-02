package com.github.calve.service.etc;

import com.github.calve.util.excel.Journals;

public interface ExcelService {
    byte[] getExcelRepresentation();

    byte[] getOutgoingExcelRepresentation();

    byte[] getTemplate(Journals journals);

}
