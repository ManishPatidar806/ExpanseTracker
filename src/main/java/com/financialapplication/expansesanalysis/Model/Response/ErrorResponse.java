package com.financialapplication.expansesanalysis.Model.Response;


import lombok.Data;
@Data
public class ErrorResponse {
    private String message;
    private boolean status;

}