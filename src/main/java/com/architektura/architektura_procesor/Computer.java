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
import static com.architektura.architektura_procesor.Enums.Opcode.MOV;
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
        for(int i = 0; i < 4;i++){
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
        processorRegisters.setInstruction_fetch_register(bitService.getTwoBytesAsOneInteger(
                                                        memory.getMemory()[processorRegisters.getInstruction_pointer()],
                                                        memory.getMemory()[processorRegisters.getInstruction_pointer() + 1]));
        
        processorRegisters.setInstruction_pointer((short) (processorRegisters.getInstruction_pointer() + 2));
    }
    
    private short decodeInstruction(){
        short IR = processorRegisters.getInstruction_fetch_register();
        return  bitService.getAllBitsBetweenPositions(IR, (byte)0, (byte) 3);   
    }
    
    public void executeInstruction(short opcode){
        System.out.println("opcode: " + opcode);
        
        short aluOperation = bitService.getAllBitsBetweenPositions(processorRegisters.getInstruction_fetch_register(), (byte)8, (byte) 11); 
        short reg1 = (short)generalRegisters.getRegister(bitService.getAllBitsBetweenPositions(processorRegisters.getInstruction_fetch_register(),(byte)4, (byte) 7));
        short reg2 = (short)generalRegisters.getRegister(bitService.getAllBitsBetweenPositions(processorRegisters.getInstruction_fetch_register(),(byte)12, (byte) 15));
        System.out.println("aluop: " + aluOperation);
         System.out.println("reg 1: " + reg1);
         System.out.println("reg 2: " + reg2);
        short result;
        switch(Opcode.fromOpcode(opcode)){
            case MOV:
                result = alu.pass(aluOperation, 
                        (short)generalRegisters.getRegister(bitService.getAllBitsBetweenPositions(processorRegisters.getInstruction_fetch_register(),(byte)4, (byte) 7)),
                        (short)generalRegisters.getRegister(bitService.getAllBitsBetweenPositions(processorRegisters.getInstruction_fetch_register(),(byte)12, (byte) 15)));
               
               generalRegisters.setRegister(bitService.getAllBitsBetweenPositions(processorRegisters.getInstruction_fetch_register(),(byte)4, (byte) 7), result);
            break;
            
             case LLDI:
                 
                result = alu.pass(aluOperation, 
                        (short)generalRegisters.getRegister(bitService.getAllBitsBetweenPositions(processorRegisters.getInstruction_fetch_register(),(byte)4, (byte) 7)),
                        bitService.getTwoBytesAsOneInteger(
                                                        memory.getMemory()[processorRegisters.getInstruction_pointer()],
                                                        memory.getMemory()[processorRegisters.getInstruction_pointer() + 1]));
                
                
               processorRegisters.setInstruction_pointer((short) (processorRegisters.getInstruction_pointer() + 2));
               generalRegisters.setRegister(bitService.getAllBitsBetweenPositions(processorRegisters.getInstruction_fetch_register(),(byte)4, (byte) 7), result);
            break;
            
            
        }
    }
   
    
    
   
    
}
