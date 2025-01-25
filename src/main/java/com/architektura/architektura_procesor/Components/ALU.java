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
    
    private void setFlags(short A, short B, short result){
         if((bitService.checkMSB(result))){
                    System.out.println("Negative bit set to 1");
                    processorRegisters.setNegative((byte)1);
                }
                else{
                    System.out.println("Negative bit set to 0");
                    processorRegisters.setNegative((byte)0);
                }
                
                if((bitService.checkMSB(result) != bitService.checkMSB(A)) && (bitService.checkMSB(result) != bitService.checkMSB(B))){
                    System.out.println("Operation result is overflow");
                    processorRegisters.setOverflow((byte)1);
                }
                else{
                   processorRegisters.setOverflow((byte)0); 
                }
                if((bitService.checkMSB(A) || bitService.checkMSB(B) && bitService.checkMSB(result)) || ((bitService.checkMSB(A) || bitService.checkMSB(B)) && !bitService.checkMSB(result))){
                    System.out.println("Operation result is carried");
                   processorRegisters.setCarry((byte)1); 
                }
                else{
                    processorRegisters.setCarry((byte)0);
                }
                if(result == 0 ){
                    System.out.println("Zero bit set to 1");
                    processorRegisters.setZero((byte)1);
                }
               else{
                    System.out.println("Zero bit set to 0");
                    processorRegisters.setZero((byte)0);
                }
    }
    
    
    public short pass(short aluop , short A, short B){
        short result;
        System.out.println("aluop:" + aluop);
        AluOperation operation = AluOperation.fromAluOperation(aluop);
        switch(operation){
            case OP_A:
                return A;  
            case OP_B:
                return B;
            case OP_ADD:
                System.out.println("Adding: " + A + " + " + B);
                result = (short) (A + B);
                setFlags(A,B,result);
                return (short) (A + B);
            case OP_SUB:
                System.out.println("Subtracting: " + A + " - " + B); 
                result = (short) (A - B);
                setFlags(A,B,result);
                return result;
            case OP_CP:
                System.out.println("Comparing by setting flags: " + A + " - " + B); 
                result = (short) (A - B);
                setFlags(A,B,result);
                return A;
            default:
                return 0;
            
        }
            
    }
   
}
