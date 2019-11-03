package com.github.calve.web.mails;

import com.github.calve.model.OutgoingMail;
import com.github.calve.service.OutgoingMailsService;
import com.github.calve.service.StorageService;
import com.github.calve.to.BaseMailTo;
import com.github.calve.to.DataTable;
import com.github.calve.to.DataTablesInput;
import com.github.calve.util.Util;
import com.github.calve.web.TransformUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity getMails(@Valid DataTablesInput dti) {
        // TODO: 03.11.2019  
        System.out.println(dti); //todo sout
        int start = dti.getStart();
        int draw = dti.getDraw();
        int page = dti.getStart() / dti.getLength();

        Sort sort = null;
        if (!dti.getOrder().isEmpty()) {
            System.out.println(dti.getOrder().get(0).getDir()); //todo sout
            System.out.println(dti.getColumns().get(dti.getOrder().get(0).getColumn()).getData()); //todo sout

            sort = Sort.by(Sort.Direction.fromString(dti.getOrder().get(0).getDir().toUpperCase()),
                    dti.getColumns().get(dti.getOrder().get(0).getColumn()).getData());

        }

        Pageable pageable = sort != null ? PageRequest.of(page, dti.getLength(), sort) :
                PageRequest.of(page, dti.getLength());

        Page<OutgoingMail> mails = service.findMails(pageable);

        DataTable dataTable = new DataTable();

        //refactoring stream
        dataTable.setData(TransformUtils.getBaseToList(mails.getContent()));
        dataTable.setRecordsTotal(mails.getTotalElements());
        dataTable.setRecordsFiltered(mails.getTotalElements());

        dataTable.setDraw(draw);
        dataTable.setStart(start);

        return ResponseEntity.ok(dataTable);

/*        @RequestParam("draw") int draw,
        @RequestParam("start") int start,
        @RequestParam("length") int length
        int page = start / length;

        Pageable pageable = PageRequest.of(page, length, new Sort(Sort.Direction.DESC, "id"));

        Page<OutgoingMail> mails = service.findMails(pageable);

        DataTable dataTable = new DataTable();

        //refactoring stream
        dataTable.setData(TransformUtils.getBaseToList(mails.getContent()));
        dataTable.setRecordsTotal(mails.getTotalElements());
        dataTable.setRecordsFiltered(mails.getTotalElements());

        dataTable.setDraw(draw);
        dataTable.setStart(start);

        return ResponseEntity.ok(dataTable);*/
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
