package com.financialapplication.expansesanalysis.Model.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Money {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

private LocalDateTime trackingStartDate;
private double creditAmount;
private double debitedAmount;
private double savingAmount;
private double income;
private double monthlyLimit;

    @JsonIgnore
@OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
