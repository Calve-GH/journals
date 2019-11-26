package com.github.calve.to;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

public class EmailTo extends BaseTo implements Serializable {
    private Integer genIndex;
    @NotBlank(message = "не может быть пустым")
    private String contact;
    @NotNull(message = "должна быть задана")
    private LocalDate date;
    private Boolean answer;
    private String description;

    public EmailTo() {
    }

    public EmailTo(Integer id, Integer genIndex, String contact, LocalDate date, Boolean answer,
                   String description) {
        super(id);
        this.genIndex = genIndex;
        this.contact = contact;
        this.date = date;
        this.answer = answer;
        this.description = description;
    }

    public void setId(Integer id) {
        super.setId(id);
    }

    public Integer getId() {
        return super.getId();
    }

    public Integer getGenIndex() {
        return genIndex;
    }

    public void setGenIndex(Integer genIndex) {
        this.genIndex = genIndex;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean getAnswer() {
        return answer;
    }

    public void setAnswer(Boolean answer) {
        this.answer = answer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
