package com.financialapplication.expansesanalysis.Model.Response;

import com.financialapplication.expansesanalysis.Model.Entity.Category;
import lombok.Data;

@Data
public class CategoryResponse {

private Category category;
private String message;
private boolean status;

public CategoryResponse(){

}
public CategoryResponse(Category category,String message,boolean status){
    this.category=category;
    this.message=message;
    this.status=status;
}



}
