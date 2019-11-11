package com.github.calve.service;

import com.github.calve.to.DataTable;
import com.github.calve.util.to.DataTablesInput;

public interface FilteredSortedData {
    DataTable findFilteredAndSort(DataTablesInput dti);
}
