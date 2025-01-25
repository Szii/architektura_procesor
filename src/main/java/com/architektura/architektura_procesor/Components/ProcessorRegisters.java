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
public class ProcessorRegisters{
    
    private short programCounter;
    private short instructionFetchRegister;
   
    //1 bit - negative
    //2 bit - overflow
    //3 bit - zero
    //4 bit - carry

    private byte pswRegister;

    public ProcessorRegisters() {
    }
    
   
    public short getProgramCounter() {
        return programCounter;
    }

    public void setProgramCounter(short programCounter) {
        this.programCounter = programCounter;
    }

    public short getInstructionFetchRegister() {
        return instructionFetchRegister;
    }

    public void setInstructionFetchRegister(short instructionFetchRegister) {
        this.instructionFetchRegister = instructionFetchRegister;
    }

    public byte getPswRegister() {
        return pswRegister;
    }

    // bit definitions for clarity
    private static final int NEGATIVE_BIT = 0;
    private static final int OVERFLOW_BIT = 1;
    private static final int ZERO_BIT     = 2;
    private static final int CARRY_BIT    = 3;

    public byte getCarry() {
        return (byte)((pswRegister >> CARRY_BIT) & 1);
    }

    public void setCarry(byte value) {
        pswRegister = (byte)(pswRegister & ~(1 << CARRY_BIT));         // clear
        pswRegister = (byte)(pswRegister | ((value & 1) << CARRY_BIT)); // set
    }

    public byte getNegative() {
        return (byte)((pswRegister >> NEGATIVE_BIT) & 1);
    }

    public void setNegative(byte value) {
        pswRegister = (byte)(pswRegister & ~(1 << NEGATIVE_BIT));          // clear
        pswRegister = (byte)(pswRegister | ((value & 1) << NEGATIVE_BIT)); // set
    }

    public byte getOverflow() {
        return (byte)((pswRegister >> OVERFLOW_BIT) & 1);
    }

    public void setOverflow(byte value) {
        pswRegister = (byte)(pswRegister & ~(1 << OVERFLOW_BIT));          // clear
        pswRegister = (byte)(pswRegister | ((value & 1) << OVERFLOW_BIT)); // set
    }

    public byte getZero() {
        return (byte)((pswRegister >> ZERO_BIT) & 1);
    }

    public void setZero(byte value) {
        pswRegister = (byte)(pswRegister & ~(1 << ZERO_BIT));          // clear
        pswRegister = (byte)(pswRegister | ((value & 1) << ZERO_BIT)); // set
    }
   
    
    public void showPSW(){
        System.out.println("PSW Zero: " + getZero());
        System.out.println("PSW Negative: " + getNegative());
        System.out.println("PSW Carry: " + getCarry());
        System.out.println("PSW Overflow: " + getNegative());
    }
  
            
}