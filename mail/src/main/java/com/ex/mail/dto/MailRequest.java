package com.ex.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MailRequest {
    private String mailTo;

    private String subject;

    private String message;
}
