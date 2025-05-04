package com.financialapplication.expansesanalysis.Model.Response;

import lombok.Data;

import java.util.Map;

@Data
public class ValidateErrorResponse {
    private Map<String ,String> message;
    private boolean status ;

}