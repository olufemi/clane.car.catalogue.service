/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clane.car.catalogue.util;

import org.springframework.stereotype.Service;

/**
 *
 * @author OSHIN
 */
@Service
public class Utility {
    
    public String returnEngineNumber(long yournumber)
    {
        return String.format("%05d", yournumber);
    }
}
