package com.github.calve.web;

import com.github.calve.model.Executor;
import com.github.calve.service.ExecutorService;
import com.github.calve.service.GenericService;
import com.github.calve.to.ExecutorTo;
import com.github.calve.to.MailTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

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
    public void create(ExecutorTo executor) {
        service.save(TransformUtils.getExecutorFromTo(executor));
    } //hardcode add converter

    @GetMapping("{id}/")
    public ExecutorTo getExecutor(@PathVariable Integer id) {
        return TransformUtils.getExecutorTo(service.findById(id));
    }

    @DeleteMapping("{id}/")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteMailById(@PathVariable Integer id) {
        service.delete(id);
    }

    @GetMapping
    public List<ExecutorTo> getExecutors() {
        return TransformUtils.getExecutorsToList(service.findAll());
    }

    @GetMapping("enabled/")
    public List<ExecutorTo> getExecutorsEnabled() {
        return TransformUtils.getExecutorsToList(service.findAllEnabled());
    }

    @PostMapping("/{id}/")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void enable(@PathVariable("id") int id, @RequestParam("enabled") boolean enabled) {
        service.enable(id, enabled);
    }
}
