/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clane.car.catalogue.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author OSHIN
 */
@Entity
@Data
@NoArgsConstructor
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    @NotEmpty(message = "CatName is mandatory")
    private String catName;
    @Column(insertable = true, updatable = false)
    private LocalDateTime RecordTimeStamp;
    private LocalDateTime RecordModifiedDate;

    public Category(String cartName) {
        this.catName = cartName;
    }

    @PrePersist
    void onCreate() {
        this.setRecordTimeStamp(LocalDateTime.now());
        this.setRecordModifiedDate(LocalDateTime.now());

    }

    @PreUpdate
    void onUpdate() {
        this.setRecordModifiedDate(LocalDateTime.now());

    }
}
