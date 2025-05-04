package com.financialapplication.expansesanalysis.Exception;

import lombok.Data;

@Data
public class CommonException extends Exception {
    private final int statusCode;

    public CommonException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}