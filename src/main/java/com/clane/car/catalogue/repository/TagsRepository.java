/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clane.car.catalogue.repository;

import com.clane.car.catalogue.domain.Tags;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author OSHIN
 */
public interface TagsRepository extends
        CrudRepository<Tags, Long>{
      boolean existsById(long id);
}
