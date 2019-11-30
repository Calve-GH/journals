package com.github.calve.repository;

import com.github.calve.model.etc.Executor;
import org.junit.jupiter.api.Test;

public class DataJpaTest extends AbstractTest {

    private final static Executor executor = new Executor("Матвеева А.И.");

    @Test
    void testContact() {
//        Contact mts = contactService.save(new Contact("MTS", "opi@mts.by"));
//Contact mts = null;
//        contactService.findAll().forEach(System.out::println);
//        List<Contact> all = contactService.findAll();
//
//        for (Contact contact : all) {
//            mts = contact;
//        }
//
//
//        System.out.println(mts.getId()); //todo sout
//
//        contactService.delete(mts.getId());

        contactService.findAll().forEach(System.out::println);
    }

    @Test
    void testIncome() {
//
//        BaseMailTo mail = new BaseMailTo();
//        mail.setOuterDate(LocalDate.now());
//        mail.setDescription("description");
//        mail.setCorrespondent("correspondent");
//        mail.setExecutor("Матвеева А.И.");
//
//        incomingService.save(mail);
//
//        incomingService.findMails().forEach(System.out::println);

    }

    @Test
    void testInbox() {
//        EmailTo email = new EmailTo();
//        email.setDate(LocalDate.now());
//        email.setContact("MTS");
//        email.setAnswer(true);
//        email.setDescription("SOME DESCRIPTION");
//        inboxService.save(email);

//        inboxService.save(email);
//        inboxService.delete(mails.get(0).getId());

//        inboxService.findMails().forEach(System.out::println);
    }

    @Test
    void testSent() {
//        EmailTo email = new EmailTo();
//        email.setDate(LocalDate.now());
//        email.setContact("MTS");
//        email.setDescription("SOME DESCRIPTION");
//
//        sentService.save(email);
//
//        sentService.findMails().forEach(System.out::println);
    }

//    @Test
//    void testPage() {
//        Pageable pageable = PageRequest.of(0, 20);
//
//        Page<Request> page = requestRepository.findAll(pageable);
//        System.out.println(page.getNumber()); //todo sout
//        System.out.println(page.getTotalElements()); //todo sout
//        System.out.println(page.getTotalPages()); //todo sout
//        System.out.println(page.getSort()); //todo sout
//        page.get().forEach(System.out::println);
//    }
//
//    @Test
//    void test0() {
//        List<Outgoing> mails = outgoingMailsService.findMails();
//        System.out.println(mails); //todo sout
//
//        List<BaseMailTo> toListFromOutgoing = TransformUtils.getBaseToList(mails);
//        System.out.println(toListFromOutgoing); //todo sout
//    }
//
//    @Test
//    void test1() {
//        Executor executorByName = executorService.findExecutorByName("Матвеева А.И.");
//        Request request = new Request(null, null, "2314", "qwer", null, "32423", "", executorByName, null, null);
//        requestRepository.save(request);
//    }
//
//    @Test
//    void test() {
//        List<Info> mails = infoService.findMails();
//        System.out.println(mails); //todo sout
//    }
//
//    @Test
//    void findAllEnabled() {
//        List<Executor> allEnabled = executorService.findAllEnabled();
//        System.out.println(allEnabled); //todo sout
//
//        List<Application> mails = service.findMails();
//        List<MailTo> toList = TransformUtils.getToList(mails);
//        System.out.println(toList); //todo sout
//
//    }
}
