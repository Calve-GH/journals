package com.github.calve.web.mails;

import com.github.calve.service.ApplicationService;
import com.github.calve.service.RequestService;
import com.github.calve.to.MailTo;
import com.github.calve.web.TransformUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = ApplicationController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ApplicationController {

    public static final String REST_URL = "/rest/applications/";

    private ApplicationService service;

    @Autowired
    public ApplicationController(ApplicationService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createMail(MailTo mail) {
        service.save(mail);
    }

    @GetMapping("{id}/")
    public MailTo getMail(@PathVariable Integer id) {
        return TransformUtils.getTo(service.findById(id));
    }

    @GetMapping
    public List<MailTo> getMails() {
        return TransformUtils.getToList(service.findMails());
    }

    @GetMapping(value = "/filter/")
    public List<MailTo> getMailsByDate(@RequestParam(value = "startDate", required = false) LocalDate from,
                                       @RequestParam(value = "endDate", required = false) LocalDate to) {
        return TransformUtils.getToList(service.findMailsBetween(from, to));
    }

    @DeleteMapping("{id}/")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteMailById(@PathVariable Integer id) {
        service.delete(id);
    }
}
