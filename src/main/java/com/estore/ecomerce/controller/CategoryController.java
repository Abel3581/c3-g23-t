
package com.estore.ecomerce.controller;

import com.estore.ecomerce.domain.Category;
import com.estore.ecomerce.service.CategoryServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin( origins = "*") 
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody CategoryRequest entity) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.addCategory(entity));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CategoryRequest entity) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.update(id, entity));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.deleteCategory(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }

     @GetMapping("/active")
    public ResponseEntity<?> findAllActive() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.listCategoryActive());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }
    @GetMapping("/inactive")
    public ResponseEntity<?> findAllInactive() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.listCategoryInactive());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

}
