package com.github.calve.service.generator;

import com.github.calve.service.spring.PostInitialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Qualifier("outgoing")
public class OutgoingIG implements IndexGenerator {
    private InitMax initMax;
    private AtomicInteger index = new AtomicInteger(0);

    @Autowired
    public OutgoingIG(InitMax initMax) {
        this.initMax = initMax;
    }

    public synchronized Integer getNextIndex() {
        return index.incrementAndGet();
    }

    @Transactional
    @PostInitialize
    public synchronized void initIndex() {
        index.set(initMax.getMaxOutgoingGI());
    }

    @Scheduled(initialDelay = 1200000, fixedRate = 1200000)
    @Transactional
    public synchronized void refreshIndex() {
        index.set(initMax.getMaxOutgoingGI());
    }
}
