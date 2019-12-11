package com.github.calve.service.etc;

import com.github.calve.model.etc.Executor;
import com.github.calve.repository.ExecutorRepository;
import com.github.calve.to.etc.DataTable;
import com.github.calve.util.to.DataTablesInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.github.calve.service.utils.JpaSpecUtils.getPageable;
import static com.github.calve.service.utils.PagingUtils.constructPage;
import static com.github.calve.to.utils.ContactTransformUtil.packExecutorList;

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
    @Caching(evict = {@CacheEvict(value = "executors", allEntries = true),
            @CacheEvict(value = "executors.enabled", allEntries = true)})
    public int delete(Integer id) {
        return repository.delete(id);
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "executors", allEntries = true),
            @CacheEvict(value = "executors.enabled", allEntries = true)})
    public Executor save(Executor executor) {
        return repository.save(executor);
    }

    @Override
    @Cacheable(value = "executors.enabled")
    public List<Executor> findAllEnabled() {
        return repository.findAllEnabled();
    }

    @Override
    public Executor findById(Integer id) {
        Optional<Executor> byId = repository.findById(id);
        return byId.orElse(null);
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "executors", allEntries = true),
            @CacheEvict(value = "executors.enabled", allEntries = true)})
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
        Page<Executor> page = findAll(getPageable(dti));
        return constructPage(dti, page, packExecutorList(page.getContent()));
    }

    @Override
    public Page<Executor> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Cacheable(value = "executors")
    public List<Executor> findAll() {
        return repository.findAll();
    }
}
