package com.github.calve.to.journal;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class RootTo implements Serializable {
    protected Integer id;
    @NotNull(message = "должна быть задана")
    protected LocalDate incomeDate;
    @NotBlank(message = "не может быть пустым")
    protected String incomeIndex;
    @NotBlank(message = "не может быть пустым")
    protected String correspondent;
    @NotNull(message = "должна быть задана")
    protected LocalDate outerDate;
    @NotNull(message = "должен быть задан")
    protected String outerIndex;
    protected String description;
    @NotBlank(message = "не может быть пустым")
    protected String executor;

    public RootTo() {
    }

    public RootTo(Integer id, LocalDate incomeDate, String incomeIndex, String correspondent, LocalDate outerDate,
                  String outerIndex, String description, String executor) {
        this.id = id;
        this.incomeDate = incomeDate;
        this.incomeIndex = incomeIndex;
        this.correspondent = correspondent;
        this.outerDate = outerDate;
        this.outerIndex = outerIndex;
        this.description = description;
        this.executor = executor;
    }
}
