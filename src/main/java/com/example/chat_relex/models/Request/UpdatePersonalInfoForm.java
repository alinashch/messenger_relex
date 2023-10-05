package com.example.chat_relex.models.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdatePersonalInfoForm {

    @NotBlank
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String value;

}
