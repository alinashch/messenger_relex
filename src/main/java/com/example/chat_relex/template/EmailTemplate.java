package com.example.chat_relex.template;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmailTemplate implements Template {

    VERIFICATION_USER("email/Verification");

    private final String name;
}