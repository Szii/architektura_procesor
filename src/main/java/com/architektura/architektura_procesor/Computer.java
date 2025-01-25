/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.architektura.architektura_procesor;

import com.architektura.architektura_procesor.Components.ALU;
import com.architektura.architektura_procesor.Components.GeneralRegisters;
import com.architektura.architektura_procesor.Components.Memory;
import com.architektura.architektura_procesor.Components.ProcessorRegisters;
import com.architektura.architektura_procesor.Configuration.MemoryConfiguration;
import com.architektura.architektura_procesor.Enums.AluOperation;
import com.architektura.architektura_procesor.Enums.Opcode;
import static com.architektura.architektura_procesor.Enums.Opcode.BASIC;
import static com.architektura.architektura_procesor.Enums.Opcode.BR_N;
import static com.architektura.architektura_procesor.Enums.Opcode.BR_NZ;
import static com.architektura.architektura_procesor.Enums.Opcode.BR_Z;
import static com.architektura.architektura_procesor.Enums.Opcode.HALT;
import static com.architektura.architektura_procesor.Enums.Opcode.LCALL;
import static com.architektura.architektura_procesor.Enums.Opcode.LD_POST_INC;
import static com.architektura.architektura_procesor.Enums.Opcode.LJMP;
import static com.architektura.architektura_procesor.Enums.Opcode.LLDI;
import static com.architektura.architektura_procesor.Enums.Opcode.NOP;
import static com.architektura.architektura_procesor.Enums.Opcode.RET;
import static com.architektura.architektura_procesor.Enums.Opcode.ST_POST_INC;
import com.architektura.architektura_procesor.Services.BitService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author brune
 */


@Component
public class Computer {
      
    ALU alu;
    GeneralRegisters generalRegisters;
    Memory memory;
    ProcessorRegisters processorRegisters;
    BitService bitService;
    MemoryConfiguration configuration;

    public Computer(ALU alu, GeneralRegisters generalRegisters, Memory memory, ProcessorRegisters processorRegisters, BitService bitService, MemoryConfiguration configuration) {
        this.alu = alu;
        this.generalRegisters = generalRegisters;
        this.memory = memory;
        this.processorRegisters = processorRegisters;
        this.bitService = bitService;
        this.configuration = configuration;
    }
    
    
    @Profile("!test")
    @EventListener(ApplicationReadyEvent.class)
    private void run(){
        loadBytesToMemoryFromFile(configuration.path);
        while(true){
            System.out.println("--------------------");
            step();
        }
    }
    
    private void step(){
        fetchInstruction();
        decodeInstruction();
        operandFetch();
        executeInstruction();
        processorRegisters.showPSW();
    }
 
    private void reset(){
        
    }
    
    
    private void loadBytesToMemoryFromFile(String path){
        try {
            String hexContent = Files.readString(Paths.get(path)).replaceAll("\\s+", "");
            System.out.println("size: " + hexContent.length());

            if (hexContent.length() % 2 != 0) {
                throw new IllegalArgumentException("Hex content length must be even to form valid bytes. This is not even: " + hexContent);
            }

            byte[] memoryArray = memory.getMemory();
            for (int i = 0; i < hexContent.length() && i / 2 < memoryArray.length; i += 2) {
                memoryArray[i / 2] = (byte) Integer.parseInt(hexContent.substring(i, i + 2), 16);
            }

            memory.setMemory(memoryArray);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + e.getMessage(), e);
        }
    }
    
    private void fetchInstruction(){
        processorRegisters.setInstructionFetchRegister(bitService.getTwoBytesAsOneShort(
                                                        memory.getMemory()[processorRegisters.getProgramCounter()],
                                                        memory.getMemory()[processorRegisters.getProgramCounter() + 1]));
        
        processorRegisters.setProgramCounter((short) (processorRegisters.getProgramCounter() + 2));
    }
    
    private void decodeInstruction() {
        short IR = processorRegisters.getInstructionFetchRegister();

        Opcode op = Opcode.fromOpcode(IR);
        System.out.println("Decoded opcode: " + op.name());
        System.out.println("instruction :" + toBinaryString(IR));
        showHexString(IR);
        processorRegisters.setOpcodeRegister(op);
    }
    
    private void operandFetch(){
        processorRegisters.setAluOperationregister((byte) bitService.getAllBitsBetweenPositions(processorRegisters.getInstructionFetchRegister(), (byte)8, (byte) 11)); 
     switch(processorRegisters.getOpcodeRegister()){
            case BASIC:
                processorRegisters.setOperandRegister1((bitService.getAllBitsBetweenPositions(processorRegisters.getInstructionFetchRegister(),(byte)4, (byte) 7)));
                processorRegisters.setOperandRegister2((bitService.getAllBitsBetweenPositions(processorRegisters.getInstructionFetchRegister(),(byte)12, (byte) 15)));
            break;
            
             case LLDI:
                processorRegisters.setOperandRegister1((bitService.getAllBitsBetweenPositions(processorRegisters.getInstructionFetchRegister(),(byte)4, (byte) 7)));
                processorRegisters.setOperandRegister2(bitService.getTwoBytesAsOneShort(
                                                        memory.getMemory()[processorRegisters.getProgramCounter()],
                                                        memory.getMemory()[processorRegisters.getProgramCounter() + 1]));
            break;
            case LD_POST_INC:
                System.out.println("Loading 16 bit value from memory");
                System.out.println("Loading from memory address: " + generalRegisters.getRegister(bitService.getAllBitsBetweenPositions(processorRegisters.getInstructionFetchRegister(),(byte)12, (byte) 15)));
                byte lowByte1 = memory.getMemory()[generalRegisters.getRegister(bitService.getAllBitsBetweenPositions(processorRegisters.getInstructionFetchRegister(),(byte)12, (byte) 15)) + 1];
                byte highByte1 = memory.getMemory()[generalRegisters.getRegister(bitService.getAllBitsBetweenPositions(processorRegisters.getInstructionFetchRegister(),(byte)12, (byte) 15))];
                
                        
               System.out.println("Expected high "  + toBinaryString(memory.getMemory()[generalRegisters.getRegister(bitService.getAllBitsBetweenPositions(processorRegisters.getInstructionFetchRegister(),(byte)12, (byte) 15))])); 
               System.out.println("Expected low "  + toBinaryString(memory.getMemory()[generalRegisters.getRegister(bitService.getAllBitsBetweenPositions(processorRegisters.getInstructionFetchRegister(),(byte)12, (byte) 15)) + 1]));        
                        
                        
                System.out.println("high byte: "  + toBinaryString(highByte1));
                System.out.println("low byte: "  + toBinaryString(lowByte1));
                
                 processorRegisters.setOperandRegister1(bitService.getAllBitsBetweenPositions(processorRegisters.getInstructionFetchRegister(),(byte)4, (byte) 7));
                 processorRegisters.setOperandRegister2(bitService.getAllBitsBetweenPositions(processorRegisters.getInstructionFetchRegister(),(byte)12, (byte) 15));
               
            break;
            case ST_POST_INC:
                System.out.println("Saving 16 bit value into memory");
                short s2 = generalRegisters.getRegister(bitService.getAllBitsBetweenPositions(processorRegisters.getInstructionFetchRegister(),(byte)12, (byte) 15));
                System.out.println("Value to be saved: " + (short)s2);
                System.out.println("Memory address: " + generalRegisters.getRegister(bitService.getAllBitsBetweenPositions(processorRegisters.getInstructionFetchRegister(),(byte)4, (byte) 7)));
                byte lowByte2 = (byte) (s2 & 0xFF);
                byte highByte2 = (byte) ((s2 >> 8) & 0xFF);
                
                System.out.println("high byte: "  + toBinaryString(highByte2));
                System.out.println("low byte: "  + toBinaryString(lowByte2));
                
                
                processorRegisters.setOperandRegister1(bitService.getAllBitsBetweenPositions(processorRegisters.getInstructionFetchRegister(),(byte)4, (byte) 7));
                processorRegisters.setOperandRegister2(bitService.getAllBitsBetweenPositions(processorRegisters.getInstructionFetchRegister(),(byte)12, (byte) 15));
         
            break;
            case LJMP:
                processorRegisters.setOperandRegister1(bitService.getTwoBytesAsOneShort(
                                                        memory.getMemory()[processorRegisters.getProgramCounter()],
                                                        memory.getMemory()[processorRegisters.getProgramCounter() + 1]));
            break;
            case LCALL:
                processorRegisters.setOperandRegister1(bitService.getTwoBytesAsOneShort(
                                                        memory.getMemory()[processorRegisters.getProgramCounter()],
                                                        memory.getMemory()[processorRegisters.getProgramCounter() + 1]));
            break;
            case BR_NZ:
               processorRegisters.setOperandRegister1(bitService.getAllBitsBetweenPositions(processorRegisters.getInstructionFetchRegister(),(byte)8, (byte) 15));  
            break;
            case BR_Z:
                processorRegisters.setOperandRegister1(bitService.getAllBitsBetweenPositions(processorRegisters.getInstructionFetchRegister(),(byte)8, (byte) 15));  
             break;
             case BR_N:
                processorRegisters.setOperandRegister1(bitService.getAllBitsBetweenPositions(processorRegisters.getInstructionFetchRegister(),(byte)8, (byte) 15));  
        }
        
    }
    
    public void executeInstruction(){
        short result;
        printProcessorState();
         switch(processorRegisters.getOpcodeRegister()){
            case BASIC:
                result = alu.pass(processorRegisters.getAluOperationregister(), 
                        generalRegisters.getRegister(processorRegisters.getOperandRegister1()),
                        generalRegisters.getRegister(processorRegisters.getOperandRegister2()));
               
               generalRegisters.setRegister(processorRegisters.getOperandRegister1(), result);
            break;
            
             case LLDI:
                 
                result = alu.pass(processorRegisters.getAluOperationregister(), 
                        processorRegisters.getOperandRegister1(),
                        processorRegisters.getOperandRegister2());
                
                
               processorRegisters.setProgramCounter((short) (processorRegisters.getProgramCounter() + 2));
               generalRegisters.setRegister(processorRegisters.getOperandRegister1(), result);
            break;
            case HALT:
                System.out.println("Program terminated");
                System.exit(0);
                
            break;
            case NOP:
                System.out.println("NOP detected, skipping");    
            break;
            case LD_POST_INC:

                System.out.println("Loading 16 bit value from memory");
                System.out.println("Loading from memory address: " + processorRegisters.getOperandRegister2());
                byte lowByte1 = memory.getMemory() [generalRegisters.getRegister(processorRegisters.getOperandRegister2()) + 1];
                byte highByte1 = memory.getMemory() [generalRegisters.getRegister(processorRegisters.getOperandRegister2())];
                              
                System.out.println("high byte: "  + toBinaryString(highByte1));
                System.out.println("low byte: "  + toBinaryString(lowByte1));
                
                short reconstructed = (short) ((highByte1 << 8) | (lowByte1 & 0xFF));
                System.out.println("Loaded value: " + reconstructed + " and saving it into register " + processorRegisters.getOperandRegister1());
                generalRegisters.setRegister(processorRegisters.getOperandRegister1(),reconstructed);
                
                //post inc
                generalRegisters.setRegister(
                        processorRegisters.getOperandRegister2(),
                        (short) (generalRegisters.getRegister(processorRegisters.getOperandRegister2()) + 2));
  
            break;
            case ST_POST_INC:
                System.out.println("Saving 16 bit value into memory");
                short s2 = generalRegisters.getRegister(processorRegisters.getOperandRegister2());
                System.out.println("Value to be saved: " + (short)s2);
                System.out.println("Memory address: " + processorRegisters.getOperandRegister1());
                byte lowByte2 = (byte) (s2 & 0xFF);
                byte highByte2 = (byte) ((s2 >> 8) & 0xFF);
                
                System.out.println("high byte: "  + toBinaryString(highByte2));
                System.out.println("low byte: "  + toBinaryString(lowByte2));
                
                //store 
                memory.getMemory()[generalRegisters.getRegister(processorRegisters.getOperandRegister1())] = highByte2;
                memory.getMemory()[(generalRegisters.getRegister(processorRegisters.getOperandRegister1()) + 1)] = lowByte2;
                System.out.println("Incrementing");
                //post inc
                generalRegisters.setRegister(
                        processorRegisters.getOperandRegister1(), 
                        (short) (generalRegisters.getRegister(processorRegisters.getOperandRegister1()) + 2));
            break;
            case LJMP:
                 processorRegisters.setProgramCounter((short) (processorRegisters.getOperandRegister1() + 2));
                 System.out.println("Jumping using 16-bit value to position: " + processorRegisters.getOperandRegister1());
            break;
            case LCALL:
                System.out.println("Jumping using 16-bit value and saving PC to register 15");
                generalRegisters.setRegister((short)0b1111, (short) (processorRegisters.getProgramCounter() + 1));
                processorRegisters.setProgramCounter(processorRegisters.getOperandRegister1());
                
                System.out.println("Register 15 containing value: " + generalRegisters.getRegister((short)0b1111));
            break;
            case RET:
                System.out.println("Returning using value from register 15");
                processorRegisters.setProgramCounter(generalRegisters.getRegister((short)0b1111));
                System.out.print("PC set to : " + generalRegisters.getRegister((short)0b1111));
                System.out.println (" Byte value: " + toBinaryString(generalRegisters.getRegister((short)0b1111)));
            break;
            case BR_NZ:
               if(processorRegisters.getZero() == 0){
                   System.out.println("If not zero, then set PC");
                   System.out.print("Setting PC to position: " + (short) ((processorRegisters.getProgramCounter() + (2 * processorRegisters.getOperandRegister1()) + 2)));
                   System.out.println (" Byte value: " + toBinaryString((short) ((processorRegisters.getProgramCounter() + (2 * processorRegisters.getOperandRegister1()) + 2))));
                   processorRegisters.setProgramCounter((short) (processorRegisters.getProgramCounter() + (2 * processorRegisters.getOperandRegister1()) + 2));
               }    
            break;
            case BR_Z:
                if(processorRegisters.getZero() == 1){
                    System.out.println("If zero, then set PC");
                    System.out.print("Setting PC to position: " + (short) ((processorRegisters.getProgramCounter() + (2 * processorRegisters.getOperandRegister1()) + 2)));
                    System.out.println (" Byte value: " + toBinaryString((short) ((processorRegisters.getProgramCounter() + (2 * processorRegisters.getOperandRegister1()) + 2))));
                    processorRegisters.setProgramCounter((short) (processorRegisters.getProgramCounter() + (2 * processorRegisters.getOperandRegister1()) + 2));
                }
             break;
             case BR_N:
                if(processorRegisters.getNegative() == 1){
                    System.out.println("If negative, then set PC");
                    System.out.print("Setting PC to position: " + (short) ((processorRegisters.getProgramCounter() + (2 * processorRegisters.getOperandRegister1()) + 2)));
                    System.out.println (" Byte value: " + toBinaryString((short) ((processorRegisters.getProgramCounter() + (2 * processorRegisters.getOperandRegister1()) + 2))));
                    processorRegisters.setProgramCounter((short) (processorRegisters.getProgramCounter() + (2 * processorRegisters.getOperandRegister1()) + 2));
                }
            break;
        }
    }
   
    public static String toBinaryString(short value) {
                int unsignedValue = value & 0xFFFF;
                String binaryString = String.format("%16s", Integer.toBinaryString(unsignedValue))
                                        .replace(' ', '0');

                return binaryString;
            }
        
       public static String toBinaryString(byte value) {
                int unsignedValue = value & 0xFF;
                String binaryString = String.format("%8s", Integer.toBinaryString(unsignedValue))
                                        .replace(' ', '0');

                return binaryString;
            }
       
       public static void showHexString(short value){
            String hexString = Integer.toHexString(Short.toUnsignedInt(value));
            System.out.println("Hexadecimal representation: " + hexString);
       }
       
       public  void printProcessorState(){
        short aluOperation = bitService.getAllBitsBetweenPositions(processorRegisters.getInstructionFetchRegister(), (byte)8, (byte) 11); 
        short reg1 = (short)generalRegisters.getRegister(bitService.getAllBitsBetweenPositions(processorRegisters.getInstructionFetchRegister(),(byte)4, (byte) 7));
        short reg2 = (short)generalRegisters.getRegister(bitService.getAllBitsBetweenPositions(processorRegisters.getInstructionFetchRegister(),(byte)12, (byte) 15));
        System.out.println("aluop: " + aluOperation);
         System.out.println("reg 1: " + reg1);
         System.out.println("reg 2: " + reg2);
         System.out.println("reg 3: " + generalRegisters.getRegister((short)3));
       }

}

 
        
    


