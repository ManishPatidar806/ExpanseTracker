package com.financialapplication.expansesanalysis.Service;

import com.financialapplication.expansesanalysis.Exception.UserNotFoundException;
import com.financialapplication.expansesanalysis.Model.Enum.CategoryType;
import com.financialapplication.expansesanalysis.Model.Request.SmsRequest;
import com.financialapplication.expansesanalysis.Model.Request.SmsUpdateRequest;
import com.financialapplication.expansesanalysis.Model.Response.CommonResponse;
import com.financialapplication.expansesanalysis.Model.Response.SmsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;



@Service
public interface SmsService {
    public ResponseEntity<CommonResponse> savedSms(SmsRequest smsRequest,String mobileNo) throws UserNotFoundException;

    public ResponseEntity<SmsResponse> getAllSms(String moblieNo) throws UserNotFoundException;

    public ResponseEntity<SmsResponse>getSmsByCategory(String mobileNo , CategoryType category) throws UserNotFoundException;

    public ResponseEntity<CommonResponse> updateCategory(String mobileNo, SmsUpdateRequest smsRequest) throws UserNotFoundException;

}
