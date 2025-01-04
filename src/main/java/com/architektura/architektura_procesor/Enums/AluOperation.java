/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.architektura.architektura_procesor.Enums;

/**
 *
 * @author brune
 */
public enum AluOperation {
    
    OP_B(0b0000), // Y <-  B, possible flags = 0000
    OP_A(0b11111), // Y <-  A, possible flags = 0000
    OP_ZERO(0b1100), //Y <- 0, possible flags = 1000 - WILL set zero flag
    OP_ADD(0b0010), //Y = A + B, possible flags - 1111    
    OP_SUB(0100); //Y = A - B, possible flags - 1111    
                              
    private final int operation;
                                          
    AluOperation(int operation) {
        this.operation = operation;
    }

    public int getOpcode() {
        return operation;
    }
    
    
    public static AluOperation fromAluOperation(short value) {
        short opcode = (short) (value & 0b1111); 
        for (AluOperation operation : values()) {
            if (operation.getOpcode() == opcode) {
                return operation;
            }
        }
        throw new IllegalArgumentException("Invalid alu operation: " + opcode);
    }
       
}
