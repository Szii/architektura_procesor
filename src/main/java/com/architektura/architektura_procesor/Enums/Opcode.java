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
    NOP((short) 0x0000, (short) 0xFFFF),
    LLDI((short) 0xE000, (short) 0xF000),
    HALT((short) 0xFFFF, (short) 0xFFFF),
    ST((short) 0x6000, (short) 0xF0F0),
    LD((short) 0x4000, (short) 0xF0F0),
    BASIC((short) 0x0000, (short) 0x0000);

    
    private final short pattern;
    private final short mask;

    Opcode(short pattern, short mask) {
        this.pattern = pattern;
        this.mask = mask;
    }

    public static Opcode fromOpcode(short value) {
        System.out.println("parameter value: " +value);
        System.out.println("Halt pattern: " + (short)0xFFFF);
        for (Opcode op : values()) {
            short masked = (short) (value & op.mask); 
            System.out.println("Masked value:   " + masked);
            if (masked == op.pattern) {
                return op;
            }
        }
        throw new IllegalArgumentException("Invalid opcode: " + value);
    }
}
