package com.github.calve.to.etc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactTo implements Serializable {
    private Integer id;
    @NotBlank(message = "не может быть пустым")
    private String name;
    private String email;
    private Boolean enabled;
}