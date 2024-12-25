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
    private short flags_register;

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

    public short getFlags_register() {
        return flags_register;
    }

    public void setFlags_register(short flags_register) {
        this.flags_register = flags_register;
    }
    
    
    
    
    
}
