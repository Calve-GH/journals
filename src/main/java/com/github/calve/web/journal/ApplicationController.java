package com.github.calve.web.journal;

import com.github.calve.service.etc.StorageService;
import com.github.calve.service.journal.ApplicationService;
import com.github.calve.to.journal.ApplicationTo;
import com.github.calve.util.Util;
import com.github.calve.util.to.DataTablesInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.sql.SQLException;

import static com.github.calve.to.journal.MailTransformUtil.packApplication;
import static com.github.calve.to.journal.MailTransformUtil.unpackApplication;

@RestController
@RequestMapping(value = ApplicationController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ApplicationController {

    static final String REST_URL = "/rest/applications/";

    private ApplicationService service;
    private StorageService storageService;

    @Autowired
    public ApplicationController(ApplicationService service, StorageService storageService) {
        this.service = service;
        this.storageService = storageService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity createMail(@Valid ApplicationTo mail, BindingResult validation) {
        return validation.hasErrors() ? Util.getFieldsErrors(validation) : getResponseOnSave(mail);
    }

    @GetMapping("{id}/")
    public ApplicationTo getMail(@PathVariable Integer id) {
        return packApplication(service.findById(id));
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

    @PostMapping("files/")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void importExcel(@RequestParam("file") MultipartFile file) throws SQLException {
        storageService.storeApplications(file);
    }

    //boilerplate code
    private ResponseEntity getResponseOnSave(ApplicationTo mail) {
        service.save(unpackApplication(mail));
        return ResponseEntity.ok().build();
    }
}
