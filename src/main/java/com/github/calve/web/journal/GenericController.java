package com.github.calve.web.journal;

import com.github.calve.service.journal.GenericService;
import com.github.calve.service.etc.StorageService;
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

import static com.github.calve.to.journal.MailTransformUtil.*;

@RestController
@RequestMapping(value = GenericController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class GenericController {

    static final String REST_URL = "/rest/generics/";

    private GenericService service;
    private StorageService storageService;

    @Autowired
    public GenericController(GenericService service, StorageService storageService) {
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
        return packGeneric(service.findById(id));
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
        storageService.storeGenerics(file);
    }

    //boilerplate code
    private ResponseEntity getResponseOnSave(DefaultTo mail) {
        service.save(unpackGeneric(mail));
        return ResponseEntity.ok().build();
    }
}
