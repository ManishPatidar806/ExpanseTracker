package com.financialapplication.expansesanalysis.Model.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class  ExpenseHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double expenseAmount;

    private LocalDateTime recordedAt;

    @JsonIgnore
@ManyToOne
@JoinColumn(name = "user_id")
    private User user;
}
