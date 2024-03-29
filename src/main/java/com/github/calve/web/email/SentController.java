package com.github.calve.web.email;

import com.github.calve.service.email.SentService;
import com.github.calve.service.etc.StorageService;
import com.github.calve.to.email.EmailTo;
import com.github.calve.to.email.SentInfo;
import com.github.calve.to.utils.EmailTransformUtil;
import com.github.calve.util.sys.ErrorFieldsUtil;
import com.github.calve.util.to.DataTablesInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.github.calve.to.utils.EmailTransformUtil.unpackSent;

@RestController
@RequestMapping(value = SentController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class SentController {
    static final String REST_URL = "/rest/sent/";

    private SentService service;
    private StorageService storageService;

    @Autowired
    public SentController(SentService service, StorageService storageService) {
        this.service = service;
        this.storageService = storageService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity save(@Valid EmailTo mail, BindingResult validation) {
        return validation.hasErrors() ? ErrorFieldsUtil.getFieldsErrors(validation) : getResponseOnSave(mail);
    }

    @PostMapping("auto/")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void saveAuto(SentInfo info) {
        service.save(info);
    }

    @GetMapping("{id}/")
    public EmailTo getMail(@PathVariable Integer id) {
        return EmailTransformUtil.packSent(service.findById(id));
    }

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
        // REFACTORING: 04.12.2019 hardcoded for handle saving;
        mail.setOption(Boolean.FALSE.toString());
        service.save(unpackSent(mail));
        return ResponseEntity.ok().build();
    }
}
