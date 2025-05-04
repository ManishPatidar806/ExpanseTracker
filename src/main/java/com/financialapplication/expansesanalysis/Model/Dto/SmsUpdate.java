package com.financialapplication.expansesanalysis.Model.Dto;

import com.financialapplication.expansesanalysis.Model.Enum.CategoryType;
import com.financialapplication.expansesanalysis.Model.Enum.MoneyType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SmsUpdate {

    @NotBlank(message = "Id must be valid")
    private long id;
    @NotBlank(message = "Amount is not Present")
    private double amount;
    @NotBlank(message = "RefNo is not Present")
    private String refNo;

    @NotBlank(message = "Category Type is not present")
    @Enumerated(EnumType.STRING)
    private CategoryType category;

    @NotBlank(message = "Money Type is not present")
    @Enumerated(EnumType.STRING)
    private MoneyType moneyType;

    @NotBlank(message = "TimeAndDate is Not present in Proper Format")
    private LocalDateTime dateTime;


}


