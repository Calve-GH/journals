package com.github.calve.service.etc;

import com.github.calve.model.etc.Executor;
import com.github.calve.to.etc.DataTable;
import com.github.calve.util.to.DataTablesInput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExecutorService {

    Executor findExecutorByName(String name);

    DataTable findFilteredAndSort(DataTablesInput dti);

    List<Executor> findAll();

    Page<Executor> findAll(Pageable pageable);

    Executor save(Executor executor);

    int delete(Integer id);

    List<Executor> findAllEnabled();

    void enable(Integer id, boolean enable);

    Executor findById(Integer id);
}
