/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clane.car.catalogue.controller;

import com.clane.car.catalogue.domain.Tags;
import com.clane.car.catalogue.repository.TagsRepository;
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
public class TagsController {

    private final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    TagsRepository _tagsRepository;

    public TagsController(TagsRepository tagsRepository) {
        this._tagsRepository = tagsRepository;
    }

    @GetMapping("/tags")
    public ResponseEntity<Iterable<Tags>> getTagInfo() {
        return ResponseEntity.ok(_tagsRepository.findAll());

    }

    @GetMapping("/tag/{id}")
    public ResponseEntity<Tags> getTagId(@PathVariable Long id) {
        Optional<Tags> tagg = _tagsRepository.findById(id);
        if (tagg.isPresent()) {
            return ResponseEntity.ok(tagg.get());
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "/create-tag", method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<?> createTag(
            @Valid @RequestBody Tags intTag,
            Errors errors) throws JsonProcessingException {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().
                    body(ValidationErrorBuilder.fromBindingErrors(errors));
        }

        try {

            Tags result = _tagsRepository.save(new Tags(intTag.getTagName()));
            return new ResponseEntity<>(result, HttpStatus.CREATED);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body("{\"statusCode\":\"0\",\"errorDescription\":\"Error Processing Your request \",\"errorCode\":\"301\"}");
        }

    }

}
