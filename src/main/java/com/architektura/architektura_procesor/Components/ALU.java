/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.architektura.architektura_procesor.Components;

import com.architektura.architektura_procesor.Enums.AluOperation;
import com.architektura.architektura_procesor.Services.BitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author brune
 */

@Component
public class ALU {
    
    @Autowired
    private final ProcessorRegisters processorRegisters;
    
    @Autowired
    private final BitService bitService;

    public ALU(ProcessorRegisters processorRegisters,  BitService bitService) {
        this.processorRegisters = processorRegisters;
        this.bitService = bitService;
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
                if((bitService.checkMSB(result))){
                    System.out.println("Adition result is negative");
                    processorRegisters.setNegative((byte)1);
                }
                else{
                    processorRegisters.setNegative((byte)0);
                }
                
                if((bitService.checkMSB(result) != bitService.checkMSB(A)) && (bitService.checkMSB(result) != bitService.checkMSB(B))){
                    System.out.println("Adition result is overflow");
                    processorRegisters.setOverflow((byte)1);
                }
                else{
                   processorRegisters.setOverflow((byte)0); 
                }
                if((bitService.checkMSB(A) || bitService.checkMSB(B) && bitService.checkMSB(result)) || ((bitService.checkMSB(A) || bitService.checkMSB(B)) && !bitService.checkMSB(result))){
                    System.out.println("Adition result is carried");
                   processorRegisters.setCarry((byte)1); 
                }
                else{
                    processorRegisters.setCarry((byte)0);
                }
                if(result == 0 ){
                    System.out.println("Adition result is zero");
                    processorRegisters.setZero((byte)1);
                }
               else{
                    processorRegisters.setZero((byte)0);
                }
                return (short) (A + B);
            default:
                return 0;
            
        }
            
    }
    
    
    
}
