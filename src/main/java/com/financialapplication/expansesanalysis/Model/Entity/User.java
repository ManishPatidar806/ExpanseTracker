package com.financialapplication.expansesanalysis.Model.Entity;

import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false , unique = true)
    private String email;
    @Column(nullable = false ,  unique = true)
    private String password;
    @Column(nullable = false)
    private String bank;
    @Column(nullable = false , unique = true)
    private String mobile;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Sms> sms;

    @OneToOne(cascade = CascadeType.ALL)
    private Money money;


    @OneToOne(cascade =CascadeType.ALL)
    private Category category;

    @OneToMany(mappedBy = "user",cascade =CascadeType.ALL,orphanRemoval = true)
    private List<ExpenseHistory> expenseHistory;

}
