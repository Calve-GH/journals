package com.github.calve.web.journal;

import com.github.calve.service.etc.StorageService;
import com.github.calve.service.journal.ComplaintService;
import com.github.calve.to.journal.ResultTo;
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

import static com.github.calve.to.journal.MailTransformUtil.packComplaint;
import static com.github.calve.to.journal.MailTransformUtil.unpackComplaint;

@RestController
@RequestMapping(value = ComplaintController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ComplaintController {

    static final String REST_URL = "/rest/complaints/";

    private ComplaintService service;
    private StorageService storageService;

    @Autowired
    public ComplaintController(ComplaintService service, StorageService storageService) {
        this.service = service;
        this.storageService = storageService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity saveMail(@Valid ResultTo mail, BindingResult validation) {
        return validation.hasErrors() ? Util.getFieldsErrors(validation) : getResponseOnSave(mail);

    }

    @GetMapping("{id}/")
    public ResultTo getMail(@PathVariable Integer id) {
        return packComplaint(service.findById(id));
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
        storageService.storeComplaints(file);
    }

    //boilerplate code
    private ResponseEntity getResponseOnSave(ResultTo mail) {
        service.save(unpackComplaint(mail));
        return ResponseEntity.ok().build();
    }
}
