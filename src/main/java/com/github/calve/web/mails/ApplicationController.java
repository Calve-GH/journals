package com.github.calve.web.mails;

import com.github.calve.service.ApplicationService;
import com.github.calve.service.StorageService;
import com.github.calve.to.MailTo;
import com.github.calve.util.Util;
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
import java.time.LocalDate;
import java.util.List;

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
    public ResponseEntity createMail(@Valid MailTo mail, BindingResult result) {
        if (result.hasErrors()) {
            return Util.getFieldsErrors(result);
        }
        service.save(mail);
        return ResponseEntity.ok().build();
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

    @PostMapping("files/")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void importExcel(@RequestParam("file") MultipartFile file) throws SQLException {
        storageService.storeApplications(file);
    }

}
