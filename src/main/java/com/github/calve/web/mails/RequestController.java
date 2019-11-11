package com.github.calve.web.mails;

import com.github.calve.service.RequestService;
import com.github.calve.service.StorageService;
import com.github.calve.to.MailTo;
import com.github.calve.util.Util;
import com.github.calve.util.to.DataTablesInput;
import com.github.calve.web.TransformUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.sql.SQLException;

@RestController
@RequestMapping(value = RequestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RequestController {
    static final String REST_URL = "/rest/requests/";

    private RequestService service;
    private StorageService storageService;

    @Autowired
    public RequestController(RequestService service, StorageService storageService) {
        this.service = service;
        this.storageService = storageService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity createMail(@Valid MailTo mail, BindingResult validation) {
        return validation.hasErrors() ? Util.getFieldsErrors(validation) : getResponseOnSave(mail);
    }

    @GetMapping("{id}/")
    public MailTo getMail(@PathVariable Integer id) {
        return TransformUtils.getTo(service.findById(id));
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
        storageService.storeRequests(file);
    }

    //boilerplate code
    private ResponseEntity getResponseOnSave(MailTo mail) {
        service.save(mail);
        return ResponseEntity.ok().build();
    }
}
