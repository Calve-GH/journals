package com.github.calve.web.journal;

import com.github.calve.service.journal.InfoService;
import com.github.calve.service.etc.StorageService;
import com.github.calve.to.journal.MailTransformUtil;
import com.github.calve.to.journal.ResultTo;
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

import static com.github.calve.to.journal.MailTransformUtil.packInfo;

@RestController
@RequestMapping(value = InfoController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class InfoController {

    static final String REST_URL = "/rest/info/";

    private InfoService service;
    private StorageService storageService;

    @Autowired
    public InfoController(InfoService service, StorageService storageService) {
        this.service = service;
        this.storageService = storageService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity createMail(@Valid ResultTo mail, BindingResult validation) {
        return validation.hasErrors() ? ErrorFieldsUtil.getFieldsErrors(validation) : getResponseOnSave(mail);
    }

    @GetMapping("{id}/")
    public ResultTo getMail(@PathVariable Integer id) {
        return packInfo(service.findById(id));
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
        storageService.storeInfo(file);
    }

    //boilerplate code
    private ResponseEntity getResponseOnSave(ResultTo mail) {
        service.save(MailTransformUtil.unpackInfo(mail));
        return ResponseEntity.ok().build();
    }
}
