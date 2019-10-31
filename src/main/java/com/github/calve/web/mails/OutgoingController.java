package com.github.calve.web.mails;

import com.github.calve.service.OutgoingMailsService;
import com.github.calve.service.StorageService;
import com.github.calve.to.BaseMailTo;
import com.github.calve.util.Util;
import com.github.calve.web.TransformUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = OutgoingController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class OutgoingController {
    static final String REST_URL = "/rest/outgoing/";

    private OutgoingMailsService service;
    private StorageService storageService;

    @Autowired
    public OutgoingController(OutgoingMailsService service, StorageService storageService) {
        this.service = service;
        this.storageService = storageService;
    }

    @PostMapping //refactoring add validation
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity createMail(BaseMailTo mail, BindingResult result) {
        if (result.hasErrors()) {
            return Util.getFieldsErrors(result);
        }
        service.save(mail);
        return ResponseEntity.ok().build();
    }
    @GetMapping("{id}/")
    public BaseMailTo getMail(@PathVariable Integer id) {
        return TransformUtils.getToFromOutgoing(service.findById(id));
    }

    @GetMapping
    public List<BaseMailTo> getMails() {
        return TransformUtils.getBaseToList(service.findMails());
    }

    @GetMapping(value = "/filter/")
    public List<BaseMailTo> getMailsByDate(@RequestParam(value = "startDate", required = false) LocalDate from,
                                           @RequestParam(value = "endDate", required = false) LocalDate to) {
        return TransformUtils.getBaseToList(service.findMailsBetween(from, to));
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
}
