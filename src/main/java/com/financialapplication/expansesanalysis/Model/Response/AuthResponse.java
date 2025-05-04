package com.financialapplication.expansesanalysis.Model.Response;

import lombok.Data;

@Data
public class AuthResponse {

    private String message;
    private boolean status;
    private String token;


}
