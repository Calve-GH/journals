package com.github.calve.service;

import com.github.calve.model.Executor;

import java.util.List;

public interface ExecutorService {

    Executor findExecutorByName(String name);

    List<Executor> findAll();

    Executor save(Executor executor);

    int delete(Integer id);

    List<Executor> findAllEnabled();

    void enable(Integer id, boolean enable);

    Executor findById(Integer id);
}
