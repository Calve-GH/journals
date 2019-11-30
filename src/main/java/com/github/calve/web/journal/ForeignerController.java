package com.github.calve.web.journal;

import com.github.calve.service.journal.ForeignerService;
import com.github.calve.service.etc.StorageService;
import com.github.calve.to.journal.ForeignerTo;
import com.github.calve.to.journal.MailTransformUtil;
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

@RestController
@RequestMapping(value = ForeignerController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ForeignerController {

    static final String REST_URL = "/rest/foreigners/";

    private ForeignerService service;
    private StorageService storageService;

    @Autowired
    public ForeignerController(ForeignerService service, StorageService storageService) {
        this.service = service;
        this.storageService = storageService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity createMail(@Valid ForeignerTo mail, BindingResult validation) {
        return validation.hasErrors() ? Util.getFieldsErrors(validation) : getResponseOnSave(mail);
    }

    @GetMapping("{id}/")
    public ForeignerTo getMail(@PathVariable Integer id) {
        return MailTransformUtil.packForeigner(service.findById(id));
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
        storageService.storeForeigners(file);
    }

    //boilerplate code
    private ResponseEntity getResponseOnSave(ForeignerTo mail) {
        service.save(MailTransformUtil.unpackForeigner(mail));
        return ResponseEntity.ok().build();
    }
}
