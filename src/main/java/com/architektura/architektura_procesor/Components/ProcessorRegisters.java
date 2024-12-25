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
    
    private short program_counter;
    private short instruction_pointer;
    private short instruction_fetch_register;
   
    //1 bit - negative
    //2 bit - overflow
    //3 bit - carry
    //4 bit - zero
    private byte flags_register;

    public ProcessorRegisters() {
    }
    
   
    public short getProgram_counter() {
        return program_counter;
    }

    public void setProgram_counter(short program_counter) {
        this.program_counter = program_counter;
    }

    public short getInstruction_pointer() {
        return instruction_pointer;
    }

    public void setInstruction_pointer(short instruction_pointer) {
        this.instruction_pointer = instruction_pointer;
    }

    public short getInstruction_fetch_register() {
        return instruction_fetch_register;
    }

    public void setInstruction_fetch_register(short instruction_fetch_register) {
        this.instruction_fetch_register = instruction_fetch_register;
    }

    public byte getFlags_register() {
        return flags_register;
    }

    public void setFlags_register(byte flags_register) {
        this.flags_register = flags_register;
    }
    
    public byte getCarry() {
        return (byte) ((flags_register >> 3) & 1);
    }

    public void setCarry(byte value) {
            flags_register = (byte) (flags_register | (value << 3));
    }
    
    public byte getNegative() {
        return (byte) ((flags_register >> 0) & 1);
    }

    public void setNegative(byte value ) {
            flags_register = (byte) (flags_register | (value << 0));
    }
    
    public byte getOverflow() {
        return (byte) ((flags_register >> 1) & 1);
    }

    public void setOverflow(byte value) {
            flags_register = (byte) (flags_register | (value << 1));
    }
    
    public byte getZero() {
        return (byte) ((flags_register >> 2) & 1);
    }

    public void setZero(byte value) {
            flags_register = (byte) (flags_register | (value << 2));
    }
   
  
            
}