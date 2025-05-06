package com.financialapplication.expansesanalysis.Service;

import com.financialapplication.expansesanalysis.Exception.NotFoundException;
import com.financialapplication.expansesanalysis.Model.Enum.CategoryType;
import com.financialapplication.expansesanalysis.Model.Request.SmsSavedRequest;
import com.financialapplication.expansesanalysis.Model.Request.SmsUpdateRequest;
import com.financialapplication.expansesanalysis.Model.Response.CommonResponse;
import com.financialapplication.expansesanalysis.Model.Response.SmsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;



@Service
public interface SmsService {
    public ResponseEntity<CommonResponse> savedSms(SmsSavedRequest smsRequest, String mobileNo) throws NotFoundException;

    public ResponseEntity<SmsResponse> getAllSms(String moblieNo) throws NotFoundException;

    public ResponseEntity<SmsResponse>getSmsByCategory(String mobileNo , CategoryType category) throws NotFoundException;

    public ResponseEntity<CommonResponse> updateCategory(String mobileNo, SmsUpdateRequest smsRequest) throws NotFoundException;

}
