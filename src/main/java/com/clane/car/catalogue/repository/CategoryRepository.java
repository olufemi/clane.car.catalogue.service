/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clane.car.catalogue.repository;

import com.clane.car.catalogue.domain.Category;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author OSHIN
 */
public interface CategoryRepository extends
        CrudRepository<Category, Long>{
    
    boolean existsById(long id);
    
}
