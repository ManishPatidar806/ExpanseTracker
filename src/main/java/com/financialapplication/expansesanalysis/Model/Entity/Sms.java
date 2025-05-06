package com.financialapplication.expansesanalysis.Model.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.financialapplication.expansesanalysis.Model.Enum.CategoryType;
import com.financialapplication.expansesanalysis.Model.Enum.MoneyType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Sms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "Amount is not Present")
    private double amount;
    @NotBlank(message = "RefNo is not Present")
    private String refNo;


    @Enumerated(EnumType.STRING)
    private CategoryType category;

    @NotBlank(message = "Money Type is not present")
    @Enumerated(EnumType.STRING)
    private MoneyType moneyType;

    @NotBlank(message = "TimeAndDate is Not present in Proper Format")
    private LocalDateTime dateTime;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
