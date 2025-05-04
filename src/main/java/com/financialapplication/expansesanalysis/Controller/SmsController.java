package com.financialapplication.expansesanalysis.Controller;

import com.financialapplication.expansesanalysis.Exception.UserNotFoundException;
import com.financialapplication.expansesanalysis.Model.Enum.CategoryType;
import com.financialapplication.expansesanalysis.Model.Request.SmsRequest;
import com.financialapplication.expansesanalysis.Model.Request.SmsUpdateRequest;
import com.financialapplication.expansesanalysis.Model.Response.CommonResponse;
import com.financialapplication.expansesanalysis.Model.Response.SmsResponse;
import com.financialapplication.expansesanalysis.Service.SmsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/sms")
public class SmsController {


    private final SmsService smsService;

    public SmsController(SmsService smsService) {
        this.smsService = smsService;
    }

    /*
     * Saved Message by filter the date of latest
     * */
    @PostMapping("/saved")
    public ResponseEntity<CommonResponse> savedSms(
            @RequestBody @Valid SmsRequest smsRequest
            , @AuthenticationPrincipal UserDetails userDetails

    ) throws UserNotFoundException {
        return smsService.savedSms(smsRequest, userDetails.getUsername());
    }

    /*
     * Get All Sms Which category is Null
     * */
    @GetMapping("/getforcategory")
    public ResponseEntity<SmsResponse> getForCategory(@AuthenticationPrincipal UserDetails userDetails) throws UserNotFoundException {
        return smsService.getAllSms(userDetails.getUsername());
    }

    /*
     * Get Sms Acc To Selected Category
     * */
    @GetMapping("/getbycategory")
    public ResponseEntity<SmsResponse> getByCategory(@AuthenticationPrincipal UserDetails userDetails
            , @RequestParam @NotBlank CategoryType category) throws UserNotFoundException {
        return smsService.getSmsByCategory(userDetails.getUsername(), category);
    }

    /*
     * Update Category
     * */
    @PostMapping("/updateCategory")
    public ResponseEntity<CommonResponse> updateCategory(@AuthenticationPrincipal UserDetails userDetails,
                                                         @RequestBody SmsUpdateRequest smsRequest) throws UserNotFoundException {
        return smsService.updateCategory(userDetails.getUsername(), smsRequest);
    }


}
