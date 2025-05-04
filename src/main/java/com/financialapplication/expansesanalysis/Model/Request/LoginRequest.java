package com.financialapplication.expansesanalysis.Model.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "Mobile number must be valid")
    private String mobileNo;
    @NotBlank(message = "password must be valid")
    private String password;

}
