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
import static com.architektura.architektura_procesor.Enums.Opcode.BASIC;

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
        executeInstruction(decodeInstruction());
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
        processorRegisters.setInstructionFetchRegister(bitService.getTwoBytesAsOneInteger(
                                                        memory.getMemory()[processorRegisters.getProgramCounter()],
                                                        memory.getMemory()[processorRegisters.getProgramCounter() + 1]));
        
        processorRegisters.setProgramCounter((short) (processorRegisters.getProgramCounter() + 2));
    }
    
    private Opcode decodeInstruction() {
        short IR = processorRegisters.getInstructionFetchRegister();

        Opcode op = Opcode.fromOpcode(IR);
        System.out.println("Decoded opcode: " + op.name());
        System.out.println("instruction :" + toBinaryString(IR));
        return op;
    }
    
    public void executeInstruction(Opcode opcode){
        System.out.println("opcode: " + opcode);
        
        short aluOperation = bitService.getAllBitsBetweenPositions(processorRegisters.getInstructionFetchRegister(), (byte)8, (byte) 11); 
        short reg1 = (short)generalRegisters.getRegister(bitService.getAllBitsBetweenPositions(processorRegisters.getInstructionFetchRegister(),(byte)4, (byte) 7));
        short reg2 = (short)generalRegisters.getRegister(bitService.getAllBitsBetweenPositions(processorRegisters.getInstructionFetchRegister(),(byte)12, (byte) 15));
        System.out.println("aluop: " + aluOperation);
         System.out.println("reg 1: " + reg1);
         System.out.println("reg 2: " + reg2);
        short result;
        switch(opcode){
            case BASIC:
                result = alu.pass(aluOperation, 
                        (short)generalRegisters.getRegister(bitService.getAllBitsBetweenPositions(processorRegisters.getInstructionFetchRegister(),(byte)4, (byte) 7)),
                        (short)generalRegisters.getRegister(bitService.getAllBitsBetweenPositions(processorRegisters.getInstructionFetchRegister(),(byte)12, (byte) 15)));
               
               generalRegisters.setRegister(bitService.getAllBitsBetweenPositions(processorRegisters.getInstructionFetchRegister(),(byte)4, (byte) 7), result);
            break;
            
             case LLDI:
                 
                result = alu.pass(aluOperation, 
                        (short)generalRegisters.getRegister(bitService.getAllBitsBetweenPositions(processorRegisters.getInstructionFetchRegister(),(byte)4, (byte) 7)),
                        bitService.getTwoBytesAsOneInteger(
                                                        memory.getMemory()[processorRegisters.getProgramCounter()],
                                                        memory.getMemory()[processorRegisters.getProgramCounter() + 1]));
                
                
               processorRegisters.setProgramCounter((short) (processorRegisters.getProgramCounter() + 2));
               generalRegisters.setRegister(bitService.getAllBitsBetweenPositions(processorRegisters.getInstructionFetchRegister(),(byte)4, (byte) 7), result);
            break;
            case HALT:
                System.out.println("Program terminated");
                System.exit(0);
                
            break;
            case NOP:
                System.out.println("NOP detected, skipping");    
            break;
            
            
        }
        
    }
    
    public static String toBinaryString(short value) {
                // Convert short to int, mask with 0xFFFF to avoid sign extension
                int unsignedValue = value & 0xFFFF;

                // Convert to binary, pad to 16 bits, and replace spaces with '0'
                String binaryString = String.format("%16s", Integer.toBinaryString(unsignedValue))
                                        .replace(' ', '0');

                return binaryString;
            }
        
    

}
