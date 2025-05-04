package com.financialapplication.expansesanalysis.Controller;

import com.financialapplication.expansesanalysis.Model.Response.CategoryResponse;
import com.financialapplication.expansesanalysis.Service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
public class CategoryController {


    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService= categoryService;
    }

    @GetMapping("/getamountofcategory")
    public ResponseEntity<CategoryResponse> getAmountOfEachCategory(@AuthenticationPrincipal UserDetails userDetails) {
        return categoryService.getAmountofCategory(userDetails.getUsername());
    }


}
