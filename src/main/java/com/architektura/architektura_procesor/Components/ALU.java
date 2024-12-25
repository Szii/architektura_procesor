/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.architektura.architektura_procesor.Components;

import com.architektura.architektura_procesor.Enums.AluOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author brune
 */

@Component
public class ALU {
    
    @Autowired
    ProcessorRegisters processorRegisters;

    public ALU(ProcessorRegisters processorRegisters) {
        this.processorRegisters = processorRegisters;
    }
    
    
    public short pass(short aluop , short A, short B){
        System.out.println("aluop:" + aluop);
        AluOperation operation = AluOperation.fromAluOperation(aluop);
        switch(operation){
            case OP_A:
                return A;  
            case OP_B:
                return B;
            case OP_ADD:
                System.out.println("Adding: " + A + " + " + B);
                short result = (short) (A + B);
                if((A > 0 || B > 0) && result <= 0){
                    processorRegisters.setCarry();
                }
                return (short) (A + B);
            default:
                return 0;
            
        }
            
    }
    
    
    
}
