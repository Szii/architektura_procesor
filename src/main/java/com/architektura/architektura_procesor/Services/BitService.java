/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.architektura.architektura_procesor.Services;

import org.springframework.stereotype.Service;

/**
 *
 * @author brune
 */


@Service
public class BitService {

    public BitService() {
    }
    
    public Byte getBitOnPosition(short value, byte position){
        return (byte) ((value >> position) & 1);
    }
    
    public Short getAllBitsFromPosition(short value, byte position){
        return (short) (value & ((1 << (16 - position)) - 1));
    }
    
    public Short getAllBitsBetweenPositions(short value, byte from,byte to){
        return (short) ((value >> from) & ((1 << (to - from + 1)) - 1));
    }
    
    
    public void setBitOnPosition(short value, byte position){
        value = (short) (value | (1 << position));
    }
    
}
