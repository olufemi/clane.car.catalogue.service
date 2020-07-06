/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clane.car.catalogue.controller;

import com.clane.car.catalogue.domain.Car;
import com.clane.car.catalogue.domain.FailedTransaction;
import com.clane.car.catalogue.repository.CarRepository;
import com.clane.car.catalogue.repository.CategoryRepository;
import com.clane.car.catalogue.repository.FailedTransactionRepo;
import com.clane.car.catalogue.repository.TagsRepository;
import com.clane.car.catalogue.util.Utility;
import com.clane.car.catalogue.validation.ValidationErrorBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.sipios.springsearch.anotation.SearchSpec;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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
public class CarController {

    private final Logger logger = LoggerFactory.getLogger(CarController.class);

    CarRepository _carRepository;
    CategoryRepository _categoryRepository;
    TagsRepository _tagsRepository;
    Utility _util;
    FailedTransactionRepo _failedTransactionRepo;
    Car carRes = new Car();

    public CarController(CarRepository carRepository, CategoryRepository categoryRepository, TagsRepository tagsRepository, Utility util, FailedTransactionRepo failedTransactionRepo) {
        this._carRepository = carRepository;
        this._categoryRepository = categoryRepository;
        this._tagsRepository = tagsRepository;
        this._util = util;
        this._failedTransactionRepo = failedTransactionRepo;
    }

    @GetMapping("/cars")
    public ResponseEntity<Iterable<Car>> getCarInfo() {
        return ResponseEntity.ok(_carRepository.findAll());

    }

    @GetMapping("/car/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        Optional<Car> car = _carRepository.findById(id);
        if (car.isPresent()) {
            return ResponseEntity.ok(car.get());
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search-car/{id}")
    public ResponseEntity<Car> searchCar(@PathVariable Long id) {
        Optional<Car> car = _carRepository.findById(id);
        if (car.isPresent()) {
            return ResponseEntity.ok(car.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/update-car/{id}")
    public ResponseEntity<?> updateCar(
            @Valid @RequestBody Car intCar, @PathVariable("id") long id,
            Errors errors) throws JsonProcessingException {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().
                    body(ValidationErrorBuilder.fromBindingErrors(errors));
        }

        Optional<Car> editCar = _carRepository.findById(id);
        logger.info(String.format("editUser >>>>>>=>%s", editCar.get()));
        carRes = editCar.get();
        if (editCar.isPresent()) {
            carRes.setDescription(intCar.getDescription());
            carRes.setName(intCar.getName());
            carRes.setCategoryId(intCar.getCategoryId());
            carRes.setImages(intCar.getImages());
            carRes.setTagsId(intCar.getTagsId());
            if (!_categoryRepository.existsById(intCar.getCategoryId())) {
                //log failed request
                FailedTransaction fTra = new FailedTransaction("Request failed, categoryId does not exist!");
                _failedTransactionRepo.save(fTra);
                return ResponseEntity.status(HttpStatus.OK)
                        .body("{\"statusCode\":\"0\",\"statusDescription\":\"Request failed, categoryId does not exist!\",\"refrenceCode\":\"204\"}");
            }

        } else {
            //log failed request
            FailedTransaction fTra = new FailedTransaction("Request failed, CarId does not exist!");
            _failedTransactionRepo.save(fTra);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("{\"statusCode\":\"0\",\"statusDescription\":\"Request failed, CarId does not exist!\",\"refrenceCode\":\"204\"}");
        }

        Car result = _carRepository.save(carRes);
        return new ResponseEntity<>(result, HttpStatus.CREATED);

    }

    @RequestMapping(value = "/create-car", method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<?> createCar(
            @Valid @RequestBody Car intCar,
            Errors errors) throws JsonProcessingException {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().
                    body(ValidationErrorBuilder.fromBindingErrors(errors));
        }
        //check if categoryId is valid
        if (!_categoryRepository.existsById(intCar.getCategoryId())) {
            //log failed request
            FailedTransaction fTra = new FailedTransaction("Request failed, categoryId does not exist!");
            _failedTransactionRepo.save(fTra);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("{\"statusCode\":\"0\",\"statusDescription\":\"Request failed, categoryId does not exist!\",\"refrenceCode\":\"204\"}");
        }
        //if not log failed request
        if (intCar.getTagsId() != 0) {
            if (!_tagsRepository.existsById(intCar.getCategoryId())) {
                //log failed request
                FailedTransaction fTra = new FailedTransaction("Request failed, tagId does not exist!");
                _failedTransactionRepo.save(fTra);
                return ResponseEntity.status(HttpStatus.OK)
                        .body("{\"statusCode\":\"0\",\"statusDescription\":\"Request failed, tagId does not exist!\",\"refrenceCode\":\"204\"}");
            }

        }
        long getLastEnteredId;
        if (_carRepository.findTopByOrderByIdDesc() == null) {

            getLastEnteredId = 1;
        } else {
            getLastEnteredId = _carRepository.findTopByOrderByIdDesc().getId() + 1;
        }

        logger.info(String.format("getLastEnteredId >>>>>>=>%s", getLastEnteredId));
        String engineNumber = _util.returnEngineNumber(getLastEnteredId);

        Car result = _carRepository.save(new Car(intCar.getName(), intCar.getDescription(), engineNumber, intCar.getCategoryId(),
                intCar.getTagsId() == 0 ? 0 : intCar.getCategoryId(), intCar.getImages()));
        return new ResponseEntity<>(result, HttpStatus.CREATED);

    }

    @GetMapping("/search-car")
    public ResponseEntity<List<Car>> searchForCars(@SearchSpec Specification<Car> specs) {
        return new ResponseEntity<>(_carRepository.findAll(Specification.where(specs)), HttpStatus.OK);
    }

}
