package com.financialapplication.expansesanalysis.Model.Request;

import com.financialapplication.expansesanalysis.Model.Dto.SmsUpdate;
import lombok.Data;

import java.util.List;

@Data
public class SmsUpdateRequest {

    private List<SmsUpdate> sms;
}
