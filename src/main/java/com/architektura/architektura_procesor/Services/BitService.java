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
    
    
    public short getAllBitsBetweenPositions(short value, byte from, byte to) {
        int unsignedValue = value & 0xFFFF;
        int fromIndex = 15 - from; 
        int toIndex = 15 - to;

        int mask = ((1 << (fromIndex + 1)) - 1) & ~((1 << toIndex) - 1);

        return (short) ((unsignedValue & mask) >> toIndex);
    }

    
    
    public short getTwoBytesAsOneShort(byte first, byte second) {
        return (short) ((first & 0xFF) << 8 | (second & 0xFF));
    }
    
    
   public boolean checkMSB(short value) {
          if( (byte) ((value >> 15) & 1) == 1){
              return true;
          }
          return false;
      }
    
}
