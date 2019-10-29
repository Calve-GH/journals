package com.github.calve.repository;

import com.github.calve.model.Executor;
import com.github.calve.model.Info;
import com.github.calve.model.OutgoingMail;
import com.github.calve.model.Request;
import com.github.calve.to.OutMailTo;
import com.github.calve.web.TransformUtils;
import com.github.calve.web.json.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class DataJpaTest extends AbstractTest {

    private final static Executor executor = new Executor("Матвеева А.И.");

    @Test
    void testPage() {
        Pageable pageable = PageRequest.of(0, 20);
        Page<Request> page = requestRepository.findAll(pageable);
        System.out.println(page.getTotalElements()); //todo sout
        System.out.println(page.getTotalPages()); //todo sout
        page.get().forEach(System.out::println);
    }

    @Test
    void test0() {
        List<OutgoingMail> mails = outgoingMailsService.findMails();
        System.out.println(mails); //todo sout

        List<OutMailTo> toListFromOutgoing = TransformUtils.getToListFromOutgoing(mails);
        System.out.println(toListFromOutgoing); //todo sout
    }

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
