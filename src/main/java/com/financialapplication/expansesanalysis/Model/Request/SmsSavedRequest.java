package com.financialapplication.expansesanalysis.Model.Request;

import com.financialapplication.expansesanalysis.Model.Entity.Sms;
import lombok.Data;

import java.util.List;

@Data
public class SmsRequest {

private List<Sms> sms;

}
