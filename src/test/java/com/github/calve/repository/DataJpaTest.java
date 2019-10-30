package com.github.calve.repository;

import com.github.calve.model.*;
import com.github.calve.to.BaseMailTo;
import com.github.calve.to.MailTo;
import com.github.calve.web.TransformUtils;
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

        List<BaseMailTo> toListFromOutgoing = TransformUtils.getBaseToList(mails);
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
//        List<Executor> allEnabled = executorService.findAllEnabled();
//        System.out.println(allEnabled); //todo sout

        List<Application> mails = service.findMails();
        List<MailTo> toList = TransformUtils.getToList(mails);
        System.out.println(toList); //todo sout

    }
}
