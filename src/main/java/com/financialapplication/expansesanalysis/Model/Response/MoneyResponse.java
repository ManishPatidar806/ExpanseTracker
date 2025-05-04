package com.financialapplication.expansesanalysis.Model.Response;

import com.financialapplication.expansesanalysis.Model.Entity.Money;
import lombok.Data;

@Data
public class MoneyResponse {


    private String message;
    private boolean status;
    private Money money;

    public MoneyResponse() {

    }

    public MoneyResponse(String message, boolean status, Money money) {
        this.message = message;
        this.status = status;
        this.money = money;
    }
}
