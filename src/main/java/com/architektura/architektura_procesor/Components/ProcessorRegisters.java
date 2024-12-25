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
    //3 bit - carry
    //4 bit - zero
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

    public void setPswRegister(byte pswRegister) {
        this.pswRegister = pswRegister;
    }
    
    public byte getCarry() {
        return (byte) ((pswRegister >> 3) & 1);
    }

    public void setCarry(byte value) {
            pswRegister = (byte) (pswRegister | (value << 3));
    }
    
    public byte getNegative() {
        return (byte) ((pswRegister >> 0) & 1);
    }

    public void setNegative(byte value ) {
            pswRegister = (byte) (pswRegister | (value << 0));
    }
    
    public byte getOverflow() {
        return (byte) ((pswRegister >> 1) & 1);
    }

    public void setOverflow(byte value) {
            pswRegister = (byte) (pswRegister | (value << 1));
    }
    
    public byte getZero() {
        return (byte) ((pswRegister >> 2) & 1);
    }

    public void setZero(byte value) {
            pswRegister = (byte) (pswRegister | (value << 2));
    }
   
  
            
}