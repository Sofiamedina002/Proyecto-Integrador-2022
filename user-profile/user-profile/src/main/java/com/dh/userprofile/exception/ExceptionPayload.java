package com.dh.userprofile.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ExceptionPayload {
    private final int statusCode;
    private final String message;
}