/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.architektura.architektura_procesor.Enums;

/**
 *
 * @author brune
 */
public enum Opcode {
      
    MOV((short)0b0000), //rd←rn, possible flags = 0000
    LLDI((short)0b1110);

    private final short opcode;
                                          
    Opcode(short opcode) {
        this.opcode = opcode;
    }

    public short getOpcode() {
        return opcode;
    }
    
    public static Opcode fromOpcode(short value) {
        short opcode = (short) (value & 0b1111); 
        for (Opcode operation : values()) {
            if (operation.getOpcode() == opcode) {
                return operation;
            }
        }
        throw new IllegalArgumentException("Invalid opcode: " + opcode);
    }
    
}
