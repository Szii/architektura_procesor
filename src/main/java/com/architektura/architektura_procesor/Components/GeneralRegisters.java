/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.architektura.architektura_procesor.Components;

import org.springframework.stereotype.Component;

/**
 *
 * @author brune
 */

@Component
public class GeneralRegisters {
    
    
    private short registers[]  = new short[3];
    
    public GeneralRegisters() {
        
    }
    
   
    public short getRegister(short register){
       return registers[register];
    }
    
    public void setRegister(short register, short value){
        System.out.println("register on position " + register + " insreting value " + value);
        registers[register] = value;
    }
    
    
    
    
    


    
    
    
}
