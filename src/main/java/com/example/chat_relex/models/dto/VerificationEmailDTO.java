package com.example.chat_relex.models.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VerificationEmailDTO {

    private String fullName;
    private String mainPage;
    private String verificationLink;
}