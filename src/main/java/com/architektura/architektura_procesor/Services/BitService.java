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
    

    public short getAllBitsFromPosition(short value, byte position) {
        // Treat the short as unsigned by masking with 0xFFFF
        int unsignedValue = value & 0xFFFF;

        // Convert position from MSB-first to LSB-first
        position = (byte) (15 - position);

        // Create a mask to keep bits from the position to the MSB
        int mask = ~((1 << position) - 1);

        // Apply the mask and return the unsigned result
        return (short) ((unsignedValue & mask) >>> position);
    }
    
    public short getAllBitsBetweenPositions(short value, byte from, byte to) {
        // Treat the short as unsigned by masking with 0xFFFF
        int unsignedValue = value & 0xFFFF;

        // Convert `from` (MSB-first) and `to` (LSB-first) to correct bit indices
        int fromIndex = 15 - from; // MSB-first position converted to 0-based LSB-first
        int toIndex = 15 - to;     // LSB-first position remains 0-based LSB-first

        // Create a mask for the range from `toIndex` to `fromIndex`
        int mask = ((1 << (fromIndex + 1)) - 1) & ~((1 << toIndex) - 1);

        // Apply the mask and shift to make the result LSB-aligned
        return (short) ((unsignedValue & mask) >> toIndex);
    }

    
    
    public short getTwoBytesAsOneInteger(byte first, byte second) {
        return (short) ((first & 0xFF) << 8 | (second & 0xFF));
    }
    
    
   public boolean checkMSB(short value) {
          if( (byte) ((value >> 15) & 1) == 1){
              return true;
          }
          return false;
      }
    
}
