package com.financialapplication.expansesanalysis.Service;

import com.financialapplication.expansesanalysis.Exception.NotFoundException;
import com.financialapplication.expansesanalysis.Model.Entity.Money;
import com.financialapplication.expansesanalysis.Model.Response.CommonResponse;
import com.financialapplication.expansesanalysis.Model.Response.MoneyResponse;
import com.financialapplication.expansesanalysis.Repository.MoneyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MoneyServiceImpl implements MoneyService {


    private final MoneyRepository moneyRepository;

    public MoneyServiceImpl(MoneyRepository moneyRepository) {
        this.moneyRepository = moneyRepository;
    }

    @Override
    public ResponseEntity<MoneyResponse> getAllAmount(String mobileNo) {
        Optional<Money> money = moneyRepository.findAmountByMoblieNo(mobileNo);
        return money.map(value -> new ResponseEntity<>(new MoneyResponse("Amount found Successfully", true, value), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(new MoneyResponse("Amount not found", true, new Money()), HttpStatus.NO_CONTENT));

    }

    @Override
    public ResponseEntity<CommonResponse> saveIncome(String mobileNo, double saving, double income) throws NotFoundException {
        if (income <= saving) {
            return new ResponseEntity<>(new CommonResponse("Income is Greater than Saving!", false), HttpStatus.BAD_REQUEST);
        }
        Money money = moneyRepository.findAmountByMoblieNo(mobileNo).orElseThrow(() -> new NotFoundException("Money Not Found"));
        money.setIncome(income);
        money.setSavingAmount(saving);
        money.setMonthlyLimit(income - saving);
        moneyRepository.save(money);
        double limit = income - saving;
        return new ResponseEntity<>(new CommonResponse("Limit Set Successfully : Rs." + limit, true), HttpStatus.OK);
    }



}
