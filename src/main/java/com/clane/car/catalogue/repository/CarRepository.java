/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clane.car.catalogue.repository;

import com.clane.car.catalogue.domain.Car;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author OSHIN
 */
public interface CarRepository extends
        CrudRepository<Car, Long>, JpaSpecificationExecutor<Car> {

    Car findTopByOrderByIdDesc();

}
