package com.github.calve.to.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutgoingTo implements Serializable {
    private Integer id;
    @NotNull(message = "должна быть задана")
    private LocalDate outerDate;
    @NotNull(message = "должен быть задан")
    private String outerIndex;
    private Integer genIndex;
    @NotNull(message = "должен быть задан")
    private String correspondent;
    private String description;
    @NotBlank(message = "не может быть пустым")
    private String executor;
}
