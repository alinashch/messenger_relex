package com.example.chat_relex.models.Response;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VerificationEmailResponse {
    private String fullName;
    private String mainPage;
    private String verificationLink;
}
