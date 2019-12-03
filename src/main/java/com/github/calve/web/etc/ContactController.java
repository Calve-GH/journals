package com.github.calve.web.etc;

import com.github.calve.service.etc.ContactService;
import com.github.calve.to.etc.ContactTo;
import com.github.calve.util.to.DataTablesInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.github.calve.to.utils.ContactTransformUtil.*;

@RestController
@RequestMapping(value = ContactController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ContactController {

    public static final String REST_URL = "/rest/contacts/";

    private ContactService service;

    @Autowired
    public ContactController(ContactService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void save(ContactTo contact) {
        service.save(unpackContact(contact));
    } //hardcode add converter

    @GetMapping("{id}/")
    public ContactTo getContactByID(@PathVariable Integer id) {
        return packContact(service.findById(id));
    }

    @DeleteMapping("{id}/")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteByID(@PathVariable Integer id) {
        service.delete(id);
    }

    @GetMapping
    public ResponseEntity getAll(@Valid DataTablesInput dti) {
        return ResponseEntity.ok(service.findFilteredAndSort(dti));
    }

    @GetMapping("enabled/")
    public List<ContactTo> getContactsEnabled() {
        return packContactList(service.findAll());
    }

}
