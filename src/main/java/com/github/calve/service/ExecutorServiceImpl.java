package com.github.calve.service;

import com.github.calve.model.Executor;
import com.github.calve.repository.ExecutorRepository;
import com.github.calve.to.DataTable;
import com.github.calve.util.to.DataTablesInput;
import com.github.calve.web.TransformUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.github.calve.service.ServiceUtils.constructExecutorsPage;

@Service
public class ExecutorServiceImpl implements ExecutorService {

    private ExecutorRepository repository;

    @Autowired
    public ExecutorServiceImpl(ExecutorRepository repository) {
        this.repository = repository;
    }

    @Override
    public Executor findExecutorByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public int delete(Integer id) {
        return repository.delete(id);
    }

    @Override
    public Executor save(Executor executor) {
        return repository.save(executor);
    }

    @Override
    public List<Executor> findAllEnabled() {
        return repository.findAllEnabled();
    }

    @Override
    public Executor findById(Integer id) {
        Optional<Executor> byId = repository.findById(id);
        return byId.orElse(null);
    }

    @Override
    public void enable(Integer id, boolean enable) {
        Optional<Executor> executorOptional = repository.findById(id);
        if (executorOptional.isPresent()) {
            Executor executor = executorOptional.get();
            executor.setEnabled(enable);
            repository.save(executor);
        }
    }

    @Override
    public DataTable findFilteredAndSort(DataTablesInput dti) {
        Pageable pageable = TransformUtils.getPageable(dti);
        return constructExecutorsPage(dti, findAll(pageable));
    }

    @Override
    public Page<Executor> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<Executor> findAll() {
        return repository.findAll();
    }
}
