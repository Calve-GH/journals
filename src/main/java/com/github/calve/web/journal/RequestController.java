package com.github.calve.web.journal;

import com.github.calve.service.etc.StorageService;
import com.github.calve.service.journal.RequestService;
import com.github.calve.to.journal.DefaultTo;
import com.github.calve.util.sys.Util;
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

import static com.github.calve.to.journal.MailTransformUtil.packRequest;
import static com.github.calve.to.journal.MailTransformUtil.unpackRequest;

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
    public ResponseEntity saveMail(@Valid DefaultTo mail, BindingResult validation) {
        return validation.hasErrors() ? Util.getFieldsErrors(validation) : getResponseOnSave(mail);
    }

    @GetMapping("{id}/")
    public DefaultTo getMail(@PathVariable Integer id) {
        return packRequest(service.findById(id));
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
    private ResponseEntity getResponseOnSave(DefaultTo mail) {
        service.save(unpackRequest(mail));
        return ResponseEntity.ok().build();
    }
}
