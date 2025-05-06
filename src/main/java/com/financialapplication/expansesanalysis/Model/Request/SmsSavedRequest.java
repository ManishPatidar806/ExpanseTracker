package com.financialapplication.expansesanalysis.Model.Request;

import com.financialapplication.expansesanalysis.Model.Dto.SmsSaved;

import lombok.Data;

import java.util.List;

@Data
public class SmsSavedRequest {

private List<SmsSaved> sms;

}
