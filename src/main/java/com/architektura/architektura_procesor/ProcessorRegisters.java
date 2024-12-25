/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.architektura.architektura_procesor;

/**
 *
 * @author brune
 */
public class ProcessorRegisters{
    
    private Short program_counter;
    private Short instruction_pointer;
    private Short instruction_fetch_register;
   
    //1 bit - negative
    //2 bit - overflow
    //3 bit - carry
    //4 bit - zero
    private Short flags_register;

    public ProcessorRegisters() {
    }
    
   
    public Short getProgram_counter() {
        return program_counter;
    }

    public void setProgram_counter(Short program_counter) {
        this.program_counter = program_counter;
    }

    public Short getInstruction_pointer() {
        return instruction_pointer;
    }

    public void setInstruction_pointer(Short instruction_pointer) {
        this.instruction_pointer = instruction_pointer;
    }

    public Short getInstruction_fetch_register() {
        return instruction_fetch_register;
    }

    public void setInstruction_fetch_register(Short instruction_fetch_register) {
        this.instruction_fetch_register = instruction_fetch_register;
    }

    public Short getFlags_register() {
        return flags_register;
    }

    public void setFlags_register(Short flags_register) {
        this.flags_register = flags_register;
    }
    
    
    
    
    
}
