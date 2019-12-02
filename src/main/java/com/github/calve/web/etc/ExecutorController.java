package com.github.calve.web.etc;

import com.github.calve.service.etc.ExecutorService;
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
@RequestMapping(value = ExecutorController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ExecutorController {

    public static final String REST_URL = "/rest/executors/";

    private ExecutorService service;

    @Autowired
    public ExecutorController(ExecutorService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void save(ContactTo executor) {
        service.save(unpackExecutor(executor));
    }

    @GetMapping("{id}/")
    public ContactTo getExecutor(@PathVariable Integer id) {
        return packExecutor(service.findById(id));
    }

    @DeleteMapping("{id}/")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteByID(@PathVariable Integer id) {
        service.delete(id);
    }

    @GetMapping
    public ResponseEntity getExecutors(@Valid DataTablesInput dti) {
        return ResponseEntity.ok(service.findFilteredAndSort(dti));
    }

    @GetMapping("enabled/")
    public List<ContactTo> getExecutorsEnabled() {
        return packExecutorList(service.findAllEnabled());
    }

    @PostMapping("/{id}/")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void enable(@PathVariable("id") int id, @RequestParam("enabled") boolean enabled) {
        service.enable(id, enabled);
    }
}
