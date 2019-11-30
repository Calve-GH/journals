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
public class IncomingTo implements Serializable {
    private Integer id;
    private Integer genIndex;
    @NotNull(message = "должна быть задана")
    private LocalDate regDate;
    private String description;
    @NotNull(message = "должен быть задан")
    private String debtor;
    @NotBlank(message = "не может быть пустым")
    private String executor;
}
