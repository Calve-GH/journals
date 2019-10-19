package com.github.calve.web.mails;

import com.github.calve.service.StorageService;
import com.github.calve.web.TransformUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.github.calve.model.Executor;
import com.github.calve.service.RequestService;
import com.github.calve.service.ExecutorService;
import com.github.calve.to.MailTo;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = RequestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RequestController {

    public static final String REST_URL = "/rest/requests/";

    private RequestService service;
    private StorageService storageService;

    @Autowired
    public RequestController(RequestService service, StorageService storageService) {
        this.service = service;
        this.storageService = storageService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createMail(MailTo mail) {
        service.save(mail);
    }

    @GetMapping("{id}/")
    public MailTo getMail(@PathVariable Integer id) {
        return TransformUtils.getTo(service.findById(id));
    }

    @GetMapping
    public List<MailTo> getMails() {
        return TransformUtils.getToList(service.findMails());
    }

    @GetMapping(value = "filter/")
    public List<MailTo> getMailsByDate(@RequestParam(value = "startDate", required = false) LocalDate from,
                                       @RequestParam(value = "endDate", required = false) LocalDate to) {
        List<MailTo> toList = TransformUtils.getToList(service.findMailsBetween(from, to));
        System.out.println("SIZE = " + toList.size()); //todo sout
        return toList;
    }

    @DeleteMapping("{id}/")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteMailById(@PathVariable Integer id) {
        service.delete(id);
    }

    @PostMapping("files/")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void importExcel(@RequestParam("file")MultipartFile file) {
        storageService.storeRequests(file);
    }
}
