package com.financialapplication.expansesanalysis.Controller;


import com.financialapplication.expansesanalysis.Exception.NotFoundException;
import com.financialapplication.expansesanalysis.Model.Response.CommonResponse;
import com.financialapplication.expansesanalysis.Model.Response.MoneyResponse;
import com.financialapplication.expansesanalysis.Service.MoneyService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/amount")
public class MoneyController {

    private final MoneyService  moneyService;

    public MoneyController(MoneyService moneyService){
        this.moneyService =moneyService;
    }

    @GetMapping("/getlimit")
    public ResponseEntity<MoneyResponse> getAllAmountDetails(@AuthenticationPrincipal UserDetails userDetails){
        return moneyService.getAllAmount(userDetails.getUsername());
    }

    @PostMapping("/saveIncome")
    public ResponseEntity<CommonResponse> saveIncome(@AuthenticationPrincipal UserDetails userDetails , @RequestParam @NotBlank(message = "Saving Is Not Present In Valid Form") double saveing,
                                                     @RequestParam @NotBlank(message = "Income is not in Valid Form") double income) throws NotFoundException {
        return moneyService.saveIncome(userDetails.getUsername(),saveing,income);
    }


}
