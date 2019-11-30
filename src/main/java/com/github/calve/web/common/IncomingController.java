package com.github.calve.web.common;

import com.github.calve.service.common.OutgoingService;
import com.github.calve.service.etc.StorageService;
import com.github.calve.to.BaseMailTo;
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
@RequestMapping(value = IncomingController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class IncomingController {
    static final String REST_URL = "/rest/incoming/";

    //refactoring too dirty;
    private OutgoingService service;
    private StorageService storageService;

    @Autowired
    public IncomingController(OutgoingService service, StorageService storageService) {
        this.service = service;
        this.storageService = storageService;
    }

    @PostMapping //refactoring add validation
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity createMail(BaseMailTo mail, BindingResult validation) {
        return validation.hasErrors() ? Util.getFieldsErrors(validation) : getResponseOnSave(mail);
    }

    @GetMapping("{id}/")
    public BaseMailTo getMail(@PathVariable Integer id) {
        return TransformUtils.getBaseMailTo(service.findById(id));
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
        storageService.storeOutgoing(file);
    }

    private ResponseEntity getResponseOnSave(BaseMailTo mail) {
        service.save(mail);
        return ResponseEntity.ok().build();
    }
}
