/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.architektura.architektura_procesor.Components;

import com.architektura.architektura_procesor.Enums.Opcode;
import org.springframework.stereotype.Component;

/**
 *
 * @author brune
 */

@Component
public class ProcessorRegisters{
    
    private short programCounter;
    private short instructionFetchRegister;
    
    private short operandRegister1;
    private short operandRegister2;
    private Opcode opcodeRegister;

    public Opcode getOpcodeRegister() {
        return opcodeRegister;
    }

    public void setOpcodeRegister(Opcode opcodeRegister) {
        this.opcodeRegister = opcodeRegister;
    }



    private byte aluOperationregister;

    public byte getAluOperationregister() {
        return aluOperationregister;
    }

    public void setAluOperationregister(byte aluOperationregister) {
        this.aluOperationregister = aluOperationregister;
    }

    public short getOperandRegister1() {
        return operandRegister1;
    }

    public void setOperandRegister1(short operandRegister1) {
        this.operandRegister1 = operandRegister1;
    }

    public short getOperandRegister2() {
        return operandRegister2;
    }

    public void setOperandRegister2(short operandRegister2) {
        this.operandRegister2 = operandRegister2;
    }
   

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

    public byte getCarry() {
        return (byte)((pswRegister >> 3) & 1);
    }

    public void setCarry(byte value) {
        pswRegister = (byte)(pswRegister & ~(1 << 3));       
        pswRegister = (byte)(pswRegister | ((value & 1) << 3)); 
    }

    public byte getNegative() {
        return (byte)((pswRegister >> 0) & 1);
    }

    public void setNegative(byte value) {
        pswRegister = (byte)(pswRegister & ~(1 << 0));          
        pswRegister = (byte)(pswRegister | ((value & 1) << 0)); 
    }

    public byte getOverflow() {
        return (byte)((pswRegister >> 1) & 1);
    }

    public void setOverflow(byte value) {
        pswRegister = (byte)(pswRegister & ~(1 << 1));        
        pswRegister = (byte)(pswRegister | ((value & 1) << 1)); 
    }

    public byte getZero() {
        return (byte)((pswRegister >> 2) & 1);
    }

    public void setZero(byte value) {
        pswRegister = (byte)(pswRegister & ~(1 << 2));        
        pswRegister = (byte)(pswRegister | ((value & 1) << 2)); 
    }
   
    
    public void showPSW(){
        System.out.println("PSW Zero: " + getZero());
        System.out.println("PSW Negative: " + getNegative());
        System.out.println("PSW Carry: " + getCarry());
        System.out.println("PSW Overflow: " + getNegative());
    }
  
            
}