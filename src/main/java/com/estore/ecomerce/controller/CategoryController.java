
package com.estore.ecomerce.controller;

import com.estore.ecomerce.domain.Category;
import com.estore.ecomerce.service.CategoryServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin( origins = "*") //recibo todo los origenes
@RequestMapping("/api/v1/category")
public class CategoryController extends BaseControllerImpl<Category, CategoryServiceImpl>{
    
}
