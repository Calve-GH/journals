package com.github.calve.web.common;

import com.github.calve.service.common.IncomingService;
import com.github.calve.service.etc.StorageService;
import com.github.calve.to.common.IncomingTo;
import com.github.calve.util.sys.ErrorFieldsUtil;
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
import java.time.LocalDate;
import java.util.Objects;

import static com.github.calve.to.utils.CommonTransformUtil.packIncoming;
import static com.github.calve.to.utils.CommonTransformUtil.unpackIncoming;

@RestController
@RequestMapping(value = IncomingController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class IncomingController {
    static final String REST_URL = "/rest/incoming/";

    //refactoring too dirty;
    private IncomingService service;
    private StorageService storageService;

    @Autowired
    public IncomingController(IncomingService service, StorageService storageService) {
        this.service = service;
        this.storageService = storageService;
    }

    @PostMapping //refactoring add validation
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity createMail(IncomingTo mail, BindingResult validation) {
        return validation.hasErrors() ? ErrorFieldsUtil.getFieldsErrors(validation) : getResponseOnSave(mail);
    }

    @GetMapping("{id}/")
    public IncomingTo getMail(@PathVariable Integer id) {
        return packIncoming(service.findById(id));
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
        storageService.storeIncoming(file);
    }



    private ResponseEntity getResponseOnSave(IncomingTo mail) {
        service.save(unpackIncoming(mail));
        return ResponseEntity.ok().build();
    }
}
