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
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author OSHIN
 */
@Entity
@Data
@NoArgsConstructor
public class Car implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotNull
    @NotBlank(message = "Description is mandatory")
    private String description;
    /*@NotNull
    @NotBlank(message = "Engine Number is mandatory")*/
    private String engineNumber;

    private long categoryId;

    @Lob
    @Column(length = 100000)
    private Byte[] images;

    private long tagsId;

    @Column(insertable = true, updatable = false)
    private LocalDateTime RecordTimeStamp;
    private LocalDateTime RecordModifiedDate;

    public Car(String name, String description, String engineNumber, long categoryId, long tagsId, Byte[] images) {
        this.name = name;
        this.description = description;
        this.engineNumber = engineNumber;
        this.categoryId = categoryId;
        this.images = images;

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
