package com.financialapplication.expansesanalysis.Service;


import com.financialapplication.expansesanalysis.Model.Response.CategoryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {

ResponseEntity<CategoryResponse> getAmountofCategory(String moblieNo);
}
