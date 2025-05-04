package com.financialapplication.expansesanalysis.Model.Request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "Name must be valid")
    private String name;

    @NotBlank(message = "Email must be Present")
    @Email(message = "Email must be ValidForm")
    private String email;

    @NotBlank(message = "Password must be valid")
    private String password;

    @NotBlank(message = "Bank Name must be valid")
    private String bank;

    @NotBlank(message = "Mobile number must be valid")
    private String mobileNo;


}
