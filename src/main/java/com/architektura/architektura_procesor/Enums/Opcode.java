/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.architektura.architektura_procesor.Enums;

/**1110111100100000
 *
 * @author brune
 */
public enum Opcode {
    NOP((short) 0x0000, (short) 0xFFFF),
    LJMP((short) 0xE010, (short) 0xF0F0),
    LCALL((short) 0xEF20, (short) 0xFFFF),
    LLDI((short) 0xE000, (short) 0xF0FF),
    HALT((short) 0xFFFF, (short) 0xFFFF),
    ST_POST_INC((short) 0x6040, (short) 0xF0F0),
    LD_POST_INC((short) 0x4040, (short) 0xF0F0),
    RET((short) 0xF0CF, (short) 0xFFFF),
    BR_NZ((short) 0xD300, (short) 0xFF00),
    BR_N((short) 0xD600, (short) 0xFF00),
    BR_Z((short) 0xD200, (short) 0xFF00),
    BASIC((short) 0x0000, (short) 0x0000);

    
    private final short pattern;
    private final short mask;

    Opcode(short pattern, short mask) {
        this.pattern = pattern;
        this.mask = mask;
    }

    public static Opcode fromOpcode(short value) {
      //  System.out.println("parameter value: " +value);
      //  System.out.println("Halt pattern: " + (short)0xFFFF);
        for (Opcode op : values()) {
            short masked = (short) (value & op.mask); 
        //    System.out.println("Masked value:   " + masked);
        //    System.out.println("Pattern value:   " + op.pattern);
            if (masked == op.pattern) {
                return op;
            }
        }
        throw new IllegalArgumentException("Invalid opcode: " + value);
    }
}
