package com.github.calve.repository;

import com.github.calve.model.Executor;
import com.github.calve.model.Info;
import com.github.calve.model.Request;
import com.github.calve.web.json.JsonUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DataJpaTest extends AbstractTest {

    private final static Executor executor = new Executor("Матвеева А.И.");

    @Test
    void test1() {
        Executor executorByName = executorService.findExecutorByName("Матвеева А.И.");
        Request request = new Request(null, null, "2314", "qwer", null, "32423", "", executorByName, null, null);
        requestRepository.save(request);
    }

    @Test
    void test() {
        List<Info> mails = infoService.findMails();
        System.out.println(mails); //todo sout
    }

    @Test
    void findAllEnabled() {
        List<Executor> allEnabled = executorService.findAllEnabled();
        System.out.println(allEnabled); //todo sout
    }
}
