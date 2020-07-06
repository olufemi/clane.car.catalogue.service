/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clane.car.catalogue.controller;

import com.clane.car.catalogue.domain.Category;
import com.clane.car.catalogue.repository.CategoryRepository;
import com.clane.car.catalogue.validation.ValidationErrorBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Optional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author OSHIN
 */
@RestController
@RequestMapping("/api")

public class CategoryController {

    CategoryRepository _categoryRepository;

    private final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    public CategoryController(CategoryRepository categoryRepository) {
        this._categoryRepository = categoryRepository;

    }

    @GetMapping("/categories")
    public ResponseEntity<Iterable<Category>> getCategoryInfo() {
        return ResponseEntity.ok(_categoryRepository.findAll());

    }

    @GetMapping("/category/{id}")
    public ResponseEntity<Category> getCategoryId(@PathVariable Long id) {
        Optional<Category> catego = _categoryRepository.findById(id);
        if (catego.isPresent()) {
            return ResponseEntity.ok(catego.get());
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "/create-cat", method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<?> createCat(
            @Valid @RequestBody Category intCat,
            Errors errors) throws JsonProcessingException {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().
                    body(ValidationErrorBuilder.fromBindingErrors(errors));
        }

        try {
            logger.info(String.format("cat >>>>>>=>%s", intCat.getCatName()));
            Category result = _categoryRepository.save(new Category(intCat.getCatName()));
            return new ResponseEntity<>(result, HttpStatus.CREATED);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body("{\"statusCode\":\"0\",\"errorDescription\":\"Error Processing Your request \",\"errorCode\":\"301\"}");
        }
    }
    
    

}
