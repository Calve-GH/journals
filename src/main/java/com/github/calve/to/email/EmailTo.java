package com.github.calve.to.email;

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
public class EmailTo implements Serializable {
    private Integer id;
    private Integer genIndex;
    @NotBlank(message = "не может быть пустым")
    private String contact;
    @NotNull(message = "должна быть задана")
    private LocalDate regDate;
    private Boolean option;
    private String description;
}
