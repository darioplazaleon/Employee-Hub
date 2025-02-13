package com.example.employessytem.dto;

public record EmailDTO(
    String destination,
    String subject,
    String body
) {}
