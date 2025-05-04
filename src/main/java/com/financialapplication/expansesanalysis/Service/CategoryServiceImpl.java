package com.financialapplication.expansesanalysis.Service;

import com.financialapplication.expansesanalysis.Model.Entity.Category;
import com.financialapplication.expansesanalysis.Model.Response.CategoryResponse;
import com.financialapplication.expansesanalysis.Repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {


    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ResponseEntity<CategoryResponse> getAmountofCategory(String moblieNo) {
        Optional<Category> category = categoryRepository.findCategoryByMoblieNo(moblieNo);
        return category.map(value -> new ResponseEntity<>(new CategoryResponse(value, "Category Found Successfully", true), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(new CategoryResponse(new Category(), "No Category Found", true), HttpStatus.NO_CONTENT));
    }
}
