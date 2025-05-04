package com.financialapplication.expansesanalysis.Model.Response;

import lombok.Data;

@Data
public class CommonResponse {

    private String message;
    private boolean status;

    public CommonResponse(String message, boolean status) {
        this.message = message;
        this.status = status;
    }

    public CommonResponse() {

    }

}
