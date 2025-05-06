package com.financialapplication.expansesanalysis.Service;

import com.financialapplication.expansesanalysis.Exception.NotFoundException;
import com.financialapplication.expansesanalysis.Model.Response.CommonResponse;
import com.financialapplication.expansesanalysis.Model.Response.MoneyResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface MoneyService {

public ResponseEntity<MoneyResponse> getAllAmount(String mobileNo);

    public ResponseEntity<CommonResponse> saveIncome(String mobileNo,double saving,double income)
            throws NotFoundException;

}
