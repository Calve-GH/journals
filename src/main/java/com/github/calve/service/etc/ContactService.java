package com.github.calve.service.etc;

import com.github.calve.model.etc.Contact;
import com.github.calve.service.FilteredSortedData;
import com.github.calve.to.etc.DataTable;
import com.github.calve.util.to.DataTablesInput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContactService extends FilteredSortedData {

    DataTable findFilteredAndSort(DataTablesInput dti);

    Contact findContactByAlias(String alias);

    List<Contact> findAll();

    Page<Contact> findAll(Pageable pageable);

    Contact save(Contact executor);

    int delete(Integer id);

    Contact findById(Integer id);
}
