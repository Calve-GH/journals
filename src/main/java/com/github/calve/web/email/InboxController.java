package com.github.calve.web.email;

import com.github.calve.service.email.InboxService;
import com.github.calve.service.etc.StorageService;
import com.github.calve.to.email.EmailTo;
import com.github.calve.util.Util;
import com.github.calve.util.to.DataTablesInput;
import com.github.calve.web.TransformUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = InboxController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class InboxController {
    static final String REST_URL = "/rest/inbox/";

    private InboxService service;
    private StorageService storageService;

    @Autowired
    public InboxController(InboxService service, StorageService storageService) {
        this.service = service;
        this.storageService = storageService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity createMail(@Valid EmailTo mail, BindingResult validation) {
        return validation.hasErrors() ? Util.getFieldsErrors(validation) : getResponseOnSave(mail);
    }

    @GetMapping("{id}/")
    public EmailTo getMail(@PathVariable Integer id) {
        return TransformUtils.getEmailTo(service.findById(id));
    }

    //refactoring
    // TODO: 26.11.2019
    @GetMapping
    public ResponseEntity getMails(@Valid DataTablesInput dti) {
        return ResponseEntity.ok(service.findFilteredAndSort(dti));
    }

    @DeleteMapping("{id}/")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteMailById(@PathVariable Integer id) {
        service.delete(id);
    }

    //boilerplate code
    private ResponseEntity getResponseOnSave(EmailTo mail) {
        service.save(mail);
        return ResponseEntity.ok().build();
    }
}
